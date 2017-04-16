package com.coffeeinfinitive.dao.enity;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by jinz on 4/16/17.
 */
@Entity

public class User {
    @Id
    private String id;
    private String username;
    private String password;

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
