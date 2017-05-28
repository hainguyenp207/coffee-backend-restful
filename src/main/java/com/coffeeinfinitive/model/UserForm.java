package com.coffeeinfinitive.model;

import com.coffeeinfinitive.dao.entity.Activity;
import com.coffeeinfinitive.dao.entity.OrgUser;
import com.coffeeinfinitive.dao.entity.Organization;
import com.coffeeinfinitive.dao.entity.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

/**
 * Created by jinz on 4/15/17.
 */

public class UserForm {
    private String username;
    private String password;
    private String email;
    private String number;
    private boolean sex;
    private String address;
    private String name;
//    @JsonProperty("organization_id")
    private String organizationId;
    private String facultyId;
    private Set<OrgUser> orgUsers;
    private Set<UserOrgForm> userOrgForm;

    @SerializedName("permissions")
    private Set<UserOrgDetailForm> userOrgDetailForms;

    public UserForm(String username,String name, String email, String number, boolean sex, String address, Set<Role> roles) {
        this.username = username;
        this.email = email;
        this.number = number;
        this.sex = sex;
        this.address = address;
        this.name = name;
    }
    public UserForm(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }


    public Set<OrgUser> getOrgUsers() {
        return orgUsers;
    }

    public void setOrgUsers(Set<OrgUser> orgUsers) {
        this.orgUsers = orgUsers;
    }

    public String getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(String facultyId) {
        this.facultyId = facultyId;
    }

    public Set<UserOrgForm> getUserOrgForm() {
        return userOrgForm;
    }

    public void setUserOrgForm(Set<UserOrgForm> userOrgForm) {
        this.userOrgForm = userOrgForm;
    }

    public Set<UserOrgDetailForm> getUserOrgDetailForms() {
        return userOrgDetailForms;
    }

    public void setUserOrgDetailForms(Set<UserOrgDetailForm> userOrgDetailForms) {
        this.userOrgDetailForms = userOrgDetailForms;
    }
}
