package com.coffeeinfinitive.service;

import com.coffeeinfinitive.dao.entity.User;

import java.util.List;

/**
 * Created by jinz on 4/16/17.
 */

public interface UserService  extends org.springframework.security.core.userdetails.UserDetailsService {
    List<User> getAllUser();
    User findUserById(String id);
    User save(User user);
    User updateUser(User user);
}
