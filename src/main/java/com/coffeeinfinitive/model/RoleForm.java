package com.coffeeinfinitive.model;

/**
 * Created by jinz on 4/15/17.
 */

public class RoleForm {
   private String id;
   private String name;

    public RoleForm(String id, String name) {
        this.id = id;
        this.name = name;
    }

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
}
