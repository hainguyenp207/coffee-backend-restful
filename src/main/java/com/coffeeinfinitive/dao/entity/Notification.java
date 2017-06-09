package com.coffeeinfinitive.dao.entity;

import java.util.Date;
import java.util.UUID;

/**
 * Created by jinz on 6/8/17.
 */
public class Notification {
    String id;
    String message;
    User user;
    Date createdDate;

    public Notification(String message, User user) {
        this.id = UUID.randomUUID().toString();
        this.message = message;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
