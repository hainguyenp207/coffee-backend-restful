package com.coffeeinfinitive.model;

import com.coffeeinfinitive.dao.entity.Organization;
import com.coffeeinfinitive.dao.entity.Role;

/**
 * Created by jinz on 4/15/17.
 */

public class UserOrgDetailForm {
   private OrganizationForm organization;
   private RoleForm role;

    public UserOrgDetailForm(OrganizationForm organization, RoleForm roleForm) {
        this.organization = organization;
        this.role = roleForm;
    }

    public OrganizationForm getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationForm organization) {
        this.organization = organization;
    }

    public RoleForm getRole() {
        return role;
    }

    public void setRole(RoleForm role) {
        this.role = role;
    }
}
