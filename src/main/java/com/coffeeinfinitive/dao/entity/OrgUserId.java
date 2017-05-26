package com.coffeeinfinitive.dao.entity;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import java.io.Serializable;

/**
 * Created by jinz on 5/21/17.
 * Map id giữa các bảng
 */
@Embeddable
public class OrgUserId implements Serializable {
    private User user;
    private Organization organization;
    private Role role;

    @ManyToOne
    public User getUser() {
        return user;
    }
    @ManyToOne
    public void setUser(User user) {
        this.user = user;
    }
    @ManyToOne
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
