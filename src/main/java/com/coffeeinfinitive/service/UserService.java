package com.coffeeinfinitive.service;

import com.coffeeinfinitive.dao.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by jinz on 4/16/17.
 */

public interface UserService {
    Page<User> getUsers(Pageable pageable);
    Page<User> getUserByOrg(String organizationId, Pageable pageable);
    User findUserById(String id);
    User save(User user);
    User updateUser(User user);
    long count();
    long countUserByOrg(String organizationId);
}
