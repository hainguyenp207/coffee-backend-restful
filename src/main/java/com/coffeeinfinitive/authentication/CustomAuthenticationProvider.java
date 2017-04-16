package com.coffeeinfinitive.authentication;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Created by jinz on 4/15/17.
 */
@Component
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        System.out.println("Name: " +  name);
        System.out.println("Password: " +  password);


        if(!name.equals("admin")){
            throw new UsernameNotFoundException("Username not found!!.");
        }
        if(!password.equals("admin")){
            throw new BadCredentialsException("Password is wrong!.");
        }

        return new UsernamePasswordAuthenticationToken(name, password, null);
    }
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
                UsernamePasswordAuthenticationToken.class);
    }
}
