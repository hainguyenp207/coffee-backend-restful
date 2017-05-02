package com.coffeeinfinitive.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jinz on 5/1/17.
 */
@RestController
public class LoginController {
    @Autowired
    PasswordEncoder passwordEncoder;
    @PostMapping(path = "/password")
    public ResponseEntity<?> getPasswordEncode(@RequestParam("password") String rawPassword){
        return new ResponseEntity(passwordEncoder.encode(rawPassword), HttpStatus.OK);
    }
}
