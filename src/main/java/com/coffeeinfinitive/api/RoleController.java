package com.coffeeinfinitive.api;

import com.coffeeinfinitive.dao.entity.Role;
import com.coffeeinfinitive.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by jinz on 5/1/17.
 */

@RestController
@RequestMapping("/api/v1/role")
public class RoleController {

    @Autowired
    RoleService roleService;
    @GetMapping(path = "/password")
    public ResponseEntity<?> getAllRole(){
        List<Role> roles = roleService.getAllRole();
        return new ResponseEntity(roles, HttpStatus.OK);
    }
}
