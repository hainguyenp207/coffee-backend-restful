package com.coffeeinfinitive.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jinz on 5/21/17.\
 * Cái bảng tào lao
 * Lấy role của user theo tổ chức
 */
@Entity
@Table(name = "org_user")
public class OrgUser implements Serializable {


    public void setPk(OrgUserId pk) {
        this.pk = pk;
    }

    @Transient
    public Organization getOrganization() {
        return getPk().getOrganization();
    }

    public void setOrganization(Organization organization) {
        getPk().setOrganization(organization);
    }

    @Transient
    public User getUser() {
        return getPk().getUser();
    }

    public void setUser(User user) {
       getPk().setUser(user);
    }

    @Transient
    public Role getRole() {
        return getPk().getRole();
    }

    public void setRole(Role role) {
        getPk().setRole(role);
    }
}
