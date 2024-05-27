package com.maima.MonApp.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.maima.MonApp.dto.PlanteDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user")
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails, Principal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullName;
     @Column(unique = true)
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private USER_ROLE role=USER_ROLE.CUSTOMER;



    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
 public boolean isCredentialsNonExpired(){
        return true;
 }

    @Override
    public boolean isEnabled() {
        return false;
    }
    @Override
    public String getUsername() {
        return fullName;
    }

    @Override
    public String getName() {
        return fullName;
    }



    public boolean hasRole(String role) {
       return getAuthorities().stream()
               .map(GrantedAuthority::getAuthority)
               .anyMatch(authority -> authority.equals(role));
    }
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();

@ElementCollection
private List<PlanteDto> favorites = new ArrayList<>();

@OneToMany(cascade = CascadeType.ALL , orphanRemoval = true )
private List <Address> addresses = new ArrayList<>();


    public List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        // Ajoutez les rôles de l'utilisateur ici
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        // Ajoutez d'autres rôles si nécessaire
        return authorities;
    }


}
