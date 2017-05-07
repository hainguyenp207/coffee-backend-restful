package com.coffeeinfinitive.service;

import com.coffeeinfinitive.dao.entity.Role;
import com.coffeeinfinitive.dao.entity.User;

import java.util.List;

/**
 * Created by jinz on 4/16/17.
 */

public interface RoleService{
    List<Role> getAllRole();
    Role findRoleById(String id);
    Role save(Role role);
    Role update(Role role);
}
