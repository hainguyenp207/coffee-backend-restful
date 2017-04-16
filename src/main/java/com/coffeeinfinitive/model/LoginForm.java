package com.coffeeinfinitive.model;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by jinz on 4/15/17.
 */

@PropertySource(value = {"classpath:validator.properties"})
public class LoginForm {
    @Autowired
    private Environment environment;

    @NotBlank( message = "{login.username.isrequired}")
    private String username;

    @NotNull(message = "{otp.invalid}")
    private String password;

    public LoginForm() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
