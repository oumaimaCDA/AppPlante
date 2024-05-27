package com.maima.MonApp.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.Key;
import java.util.List;

@Service
public class JwtTokenValidator extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
             @NonNull HttpServletResponse response,
             @NonNull FilterChain filterChain )
            throws ServletException, IOException {

        String jwt = request.getHeader(JwtConstant.JWT_HEADER);
              //Bearer token
         if(jwt != null){
                jwt = jwt.substring(7);

                try{
            SecretKey Key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
            Claims claims = Jwts.parserBuilder().setSigningKey(Key).build().parseClaimsJws(jwt).getBody();

           String email= String.valueOf(claims.get("email"));
           String authorities= String.valueOf((claims.get("authorities")));

          // ROLE_CUSTOMER, ROLE_ADMIN

           List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
           Authentication authentication= new UsernamePasswordAuthenticationToken(email,null,auth);
           SecurityContextHolder.getContext().setAuthentication(authentication);

       }
         catch (Exception e){
         //  throw new BadCredentialsException("invalid token.....");

        }
  }
         filterChain.doFilter(request,response);
    }
}
