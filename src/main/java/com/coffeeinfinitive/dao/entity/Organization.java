package com.coffeeinfinitive.dao.entity;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by jinz on 4/27/17.
 */
@Entity

@Table(name = "organization")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Organization {
    private String id;
    private String name;
    private String description;
    private Set<Activity> activities;
    private Set<OrgUser> orgUsers = new HashSet<>(0);

    public Organization(){
        this.id = UUID.randomUUID().toString();
    }
    public Organization(String name, String description){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organization")
    @JsonBackReference
    public Set<Activity> getActivities() {
        return activities;
    }

    public void setActivities(Set<Activity> activities) {
        this.activities = activities;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.organization")
    public Set<OrgUser> getOrgUsers() {
        return orgUsers;
    }

    public void setOrgUsers(Set<OrgUser> orgUsers) {
        this.orgUsers = orgUsers;
    }
}
