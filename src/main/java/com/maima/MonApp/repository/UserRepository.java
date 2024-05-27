package com.maima.MonApp.repository;


import com.maima.MonApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Long> {

 public User findByEmail(String username);
}
