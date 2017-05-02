package com.coffeeinfinitive.dao.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by jinz on 4/16/17.
 */
@Entity
@Table(name = "role")
public class Role implements Serializable {
    private String id;
    private String name;
    private Set<User> users = new HashSet<>(0);

    public Role() {
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

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
