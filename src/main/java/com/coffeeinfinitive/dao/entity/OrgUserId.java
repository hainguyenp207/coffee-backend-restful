package com.coffeeinfinitive.dao.entity;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
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

    @ManyToOne
    public Role getRole() {
        return role;
    }

    public void setRole(Role role){
        this.role = role;
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrgUserId that = (OrgUserId) o;

        if ( user!= null ? !user.equals(that.user) : that.user != null) return false;
        if (organization != null ? !organization.equals(that.organization) : that.organization != null)
            return false;
        if (role != null ? !role.equals(that.role) : that.role != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (user != null ? user.hashCode() : 0);
        result = 31 * result + (organization != null ? organization.hashCode() : 0);
        result = 32 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

}
