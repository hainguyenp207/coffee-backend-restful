package com.coffeeinfinitive.security;

import java.io.Serializable;

/**
 * Created by jinz on 5/1/17.
 */
public class CoffeeJwtAuthenticationRequest implements Serializable {
    private static final long serialVersionUID = -8445943548965154778L;

    private String username;
    private String password;

    public CoffeeJwtAuthenticationRequest() {
        super();
    }

    public CoffeeJwtAuthenticationRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
