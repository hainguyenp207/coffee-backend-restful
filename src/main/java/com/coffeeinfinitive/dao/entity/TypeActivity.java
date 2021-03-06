package com.coffeeinfinitive.dao.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

/**
 * Created by jinz on 4/16/17.
 */
@Entity
@Table(name = "type_activity")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TypeActivity {

    private String id;
    private String name;
    private String description;
    private Set<Activity> activities;

    public TypeActivity(){
        this.id = UUID.randomUUID().toString();
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
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "typeActivity")
    @JsonManagedReference(value = "type-activity")
    public Set<Activity> getActivities() {
        return activities;
    }

    public void setActivities(Set<Activity> activities) {
        this.activities = activities;
    }
}
