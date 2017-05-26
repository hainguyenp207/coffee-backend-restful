package com.coffeeinfinitive.service.impl;

import com.coffeeinfinitive.constants.ResultCode;
import com.coffeeinfinitive.dao.entity.Role;
import com.coffeeinfinitive.dao.entity.User;
import com.coffeeinfinitive.dao.reponsitories.RoleRepository;
import com.coffeeinfinitive.dao.reponsitories.UserRepository;
import com.coffeeinfinitive.exception.CoffeeAuthException;
import com.coffeeinfinitive.service.RoleService;
import com.coffeeinfinitive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by jinz on 4/16/17.
 */
@Service("roleService")
@Transactional
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }

    @Override
    public Page<Role> getRoleByPage(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    @Override
    public long count() {
        return roleRepository.count();
    }

    @Override
    public Role findRoleById(String id) {
        return roleRepository.findOne(id);
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role update(Role role) {
        return roleRepository.save(role);
    }
}