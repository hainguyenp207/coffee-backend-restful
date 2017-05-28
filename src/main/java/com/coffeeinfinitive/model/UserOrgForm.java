package com.coffeeinfinitive.model;

import com.coffeeinfinitive.dao.entity.OrgUser;
import com.coffeeinfinitive.dao.entity.Role;

import java.util.Set;

/**
 * Created by jinz on 4/15/17.
 */

public class UserOrgForm {
   private String organizationId;
   private String roleId;

    public UserOrgForm(String organizationId, String roleId) {
        this.organizationId = organizationId;
        this.roleId = roleId;
    }

    public UserOrgForm() {
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
