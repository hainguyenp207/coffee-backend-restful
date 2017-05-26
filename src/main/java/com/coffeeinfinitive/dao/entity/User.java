package com.coffeeinfinitive.dao.entity;

import com.fasterxml.jackson.annotation.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Created by jinz on 4/16/17.
 */
@Entity
@Table(name = "user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="username")
    public class User implements Serializable, UserDetails {

    private String username;
    private String password;
    private String name;
    private String email;
    private String number;
    private boolean sex;
    private String address;
    private String organizationId;
    private Set<Role> roles;
    private Set<Activity> activitiesCreated;
    private Set<Activity> activitiesUpdated;
    private Collection<SimpleGrantedAuthority> authorities;
    private Date lastPasswordResetDate;
    private Set<Comment> comments;
    private Faculty faculty;
    private Set<OrgUser> orgUsers = new HashSet<OrgUser>(0);

    public User(){
    }

    public User(String username, String email, String number, boolean sex, String address,
                Set<Role> roles) {
        this.username = username;
        this.email = email;
        this.number = number;
        this.sex = sex;
        this.address = address;
        this.roles = roles;
    }
    @Id
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    @Transient
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Transient
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Transient
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Transient
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Transient
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        authorities = new HashSet<SimpleGrantedAuthority>(roles.size());
        for (Role role : roles)
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        return authorities;
    }
    public void setAuthorities(Collection<SimpleGrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }
    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UsernamePasswordAuthenticationToken toAuthenticationToken() {
        return new UsernamePasswordAuthenticationToken(username, password, getAuthorities());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.user")
    public Set<OrgUser> getOrgUsers() {
        return orgUsers;
    }

    public void setOrgUsers(Set<OrgUser> orgUsers) {
        this.orgUsers = orgUsers;
    }

    @Column(name = "organization_id")
    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    @JsonIgnore
    @Column(name = "last_password_reset_date")
    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "createdBy")
    @JsonIgnore
    public Set<Activity> getActivitiesCreated() {
        return activitiesCreated;
    }

    public void setActivitiesCreated(Set<Activity> activitiesCreated) {
        this.activitiesCreated = activitiesCreated;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lastUpdatedBy")
    @JsonIgnore
    public Set<Activity> getActivitiesUpdated() {
        return activitiesUpdated;
    }

    public void setActivitiesUpdated(Set<Activity> activitiesUpdated) {
        this.activitiesUpdated = activitiesUpdated;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    @JsonIgnore
    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id",updatable = false,insertable = false)
    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }
}
