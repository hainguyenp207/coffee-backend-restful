package com.coffeeinfinitive.model;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Created by jinz on 4/15/17.
 */

public class OrganizationForm {
    private String id;
   private String name;
   private String description;

    public OrganizationForm() {
        this.id = UUID.randomUUID().toString();
    }

    public OrganizationForm(boolean genernateId, String id, String name) {
        if(genernateId)
        this.id = UUID.randomUUID().toString();
        else
            this.id = id;
        this.name = name;
    }

    public OrganizationForm(String name, String description) {
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
