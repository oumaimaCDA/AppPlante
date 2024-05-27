package com.maima.MonApp.service;

import com.maima.MonApp.model.User;

public interface UserService {


public User findUserByJwtToken(String jwt) throws Exception;

public User findUserByEmail(String email) throws Exception;

}
