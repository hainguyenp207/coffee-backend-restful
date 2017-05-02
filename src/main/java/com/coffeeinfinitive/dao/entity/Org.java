package com.coffeeinfinitive.dao.entity;

import javax.persistence.Id;
import java.util.Set;
import java.util.UUID;

/**
 * Created by jinz on 4/27/17.
 */
public class Org {
    private String id;
    private String name;
    private Set<Activity> activitys;

    public Org(String name){
        this.id = UUID.randomUUID().toString();
        this.name = name;
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

    public Set<Activity> getActivitys() {
        return activitys;
    }

    public void setActivitys(Set<Activity> activitys) {
        this.activitys = activitys;
    }
}
