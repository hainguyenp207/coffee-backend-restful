package com.coffeeinfinitive.api;

import com.coffeeinfinitive.dao.entity.Role;
import com.coffeeinfinitive.model.RoleForm;
import com.coffeeinfinitive.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinz on 5/1/17.
 */

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    @Autowired
    RoleService roleService;
    @GetMapping()
    public ResponseEntity<?> getAllRole(){
        List<Role> roles = roleService.getAllRole();
        List<RoleForm> roleForms = new ArrayList<>();
        roles.forEach(role -> {
            RoleForm roleF = new RoleForm(role.getId(), role.getName());
            roleForms.add(roleF);
        });
        return new ResponseEntity(roleForms, HttpStatus.OK);
    }
}
