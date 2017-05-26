package com.coffeeinfinitive.service;

import com.coffeeinfinitive.dao.entity.Role;
import com.coffeeinfinitive.dao.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by jinz on 4/16/17.
 */

public interface RoleService{
    List<Role> getAllRole();
    Page<Role> getRoleByPage(Pageable pageable);
    long count();
    Role findRoleById(String id);
    Role save(Role role);
    Role update(Role role);
}
