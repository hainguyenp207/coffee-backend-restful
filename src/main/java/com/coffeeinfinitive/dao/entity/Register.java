package com.coffeeinfinitive.dao.entity;

import javax.persistence.Id;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

/**
 * Created by jinz on 4/27/17.
 * Bảng đăng ký hoạt động của user
 */
public class Register {
    private String id;
    private User user;
    private Activity activity;
    private Date createdDate;
    private User createdBy;

    public Register(String name){
        this.id = UUID.randomUUID().toString();
    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}
