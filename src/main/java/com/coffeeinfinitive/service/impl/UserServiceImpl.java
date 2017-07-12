package com.coffeeinfinitive.service.impl;

import com.coffeeinfinitive.constants.ResultCode;
import com.coffeeinfinitive.dao.entity.User;
import com.coffeeinfinitive.dao.reponsitories.UserOrgRepository;
import com.coffeeinfinitive.dao.reponsitories.UserRepository;
import com.coffeeinfinitive.exception.CoffeeAuthException;
import com.coffeeinfinitive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by jinz on 4/16/17.
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserOrgRepository userOrgRepository;

    @Override
    public Page<User> getUserByOrg(String orgId, Pageable pageable) {
        return userOrgRepository.getUserByOrg(orgId, pageable);
    }

    @Override
    public long countUserByOrg(String orgId) {
        return userOrgRepository.countOrgUserByOrganization(orgId);
    }

    @Override
    public User findUserById(String id) {
        return userRepository.findOne(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}