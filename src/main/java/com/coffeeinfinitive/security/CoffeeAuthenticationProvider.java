package com.coffeeinfinitive.security;

import com.coffeeinfinitive.constants.ResultCode;
import com.coffeeinfinitive.dao.entity.User;
import com.coffeeinfinitive.exception.CoffeeAuthException;
import com.coffeeinfinitive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Created by jinz on 5/1/17.
 */
@Component
public class CoffeeAuthenticationProvider  implements AuthenticationProvider {

    @Autowired
    UserService userService;

    @Bean
    protected BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public Authentication authenticate(Authentication authentication){

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userService.findUserById(username);
        if(user==null){
            throw new CoffeeAuthException(ResultCode.USER_NOT_FOUND.getCode(),
                    ResultCode.USER_NOT_FOUND.getMessageVn());
        }

        if(passwordEncoder().matches(password,user.getPassword())){
            // use the credentials
            // and authenticate against the third-party system
            return new UsernamePasswordAuthenticationToken(username
                    ,null, null);
        }else{
            throw new CoffeeAuthException(ResultCode.BAD_CREDENTIAL.getCode(),
                    ResultCode.BAD_CREDENTIAL.getMessageVn());
        }


    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(
                UsernamePasswordAuthenticationToken.class);
    }
}
