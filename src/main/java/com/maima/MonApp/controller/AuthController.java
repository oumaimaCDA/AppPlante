package com.maima.MonApp.controller;

import com.maima.MonApp.config.JwtProvider;
import com.maima.MonApp.model.Cart;
import com.maima.MonApp.model.USER_ROLE;
import com.maima.MonApp.model.User;
import com.maima.MonApp.repository.CartRepository;
import com.maima.MonApp.repository.UserRepository;
import com.maima.MonApp.request.LoginRequest;
import com.maima.MonApp.response.AuthResponse;
import com.maima.MonApp.service.CustomerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;
    @Autowired
    private CartRepository cartRepository;



@PostMapping("/signup")
@ResponseStatus(HttpStatus.ACCEPTED)
public ResponseEntity<AuthResponse>createUserHandler(@RequestBody @Validated User user) throws Exception{

    User isEmailExist=userRepository.findByEmail(user.getEmail());
    if(isEmailExist!=null){
        throw  new Exception("Email is already used with another account");
    }
User createdUser = new User();
    createdUser.setEmail(user.getEmail());
    createdUser.setFullName(user.getFullName());
    createdUser.setRole(user.getRole());
    createdUser.setPassword(passwordEncoder.encode(user.getPassword()));

    User saveUser=userRepository.save(createdUser);

    Cart cart=new Cart();
    cart.setCustomer(saveUser);
    cartRepository.save(cart);

    Authentication authentication= new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
    SecurityContextHolder.getContext().setAuthentication(authentication);

    String jwt = jwtProvider.generateToken(authentication);
    AuthResponse authResponse=new AuthResponse();
    authResponse.setJwt(jwt);
    authResponse.setMessage("Register success");
    authResponse.setRole(saveUser.getRole());


    return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
}

@PostMapping("/signin")
public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest req){

    String username =req.getEmail();
    String password =req.getPassword();

    Authentication authentication= authenticate(username,password);

    Collection<? extends GrantedAuthority>authorities= authentication.getAuthorities();
    String role=authorities.isEmpty()?null:authorities.iterator().next().getAuthority();

    String jwt = jwtProvider.generateToken(authentication);
    AuthResponse authResponse=new AuthResponse();
    authResponse.setJwt(jwt);
    authResponse.setMessage("Login success");
    authResponse.setRole(USER_ROLE.valueOf(role));


    return new ResponseEntity<>(authResponse, HttpStatus.OK);

}

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);

        if(userDetails==null){
            throw new BadCredentialsException("invalid username...");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("invalid password...");

        }
        return  new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}




















