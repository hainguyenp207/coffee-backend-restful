package com.coffeeinfinitive.service;

import com.coffeeinfinitive.dao.enity.User;

import java.util.List;

/**
 * Created by jinz on 4/16/17.
 */

public interface UserService {
    User findById(Long id);

    User findByName(String name);

    void saveUser(User user);

    void updateUser(User user);

    void deleteUserById(Long id);

    void deleteAllUsers();

    List<User> findAllUsers();

    boolean isUserExist(User user);;
}
