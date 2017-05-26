package com.coffeeinfinitive.model;

import com.coffeeinfinitive.dao.entity.Organization;
import com.coffeeinfinitive.dao.entity.Role;
import com.coffeeinfinitive.dao.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

/**
 * Created by jinz on 4/15/17.
 */

public class ActivityForm {
    private String id;
    private String name;
    private String description;
    private String organizationId;
    private Organization organization;
    private Date startDate;
    private Date endDate;
    private Date createdDate;
    private Date lastUpdatedDate;
    private boolean confirmed;
    private String activityTypeId;
    private int pointTranning;
    private int pointSocial;
    private String status;

    public ActivityForm() {
        this.id = UUID.randomUUID().toString();
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


    public Date getStartDate() {
        return startDate;
    }

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm",timezone = "Asia/Ho_Chi_Minh")
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm",timezone = "Asia/Ho_Chi_Minh")
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm",timezone = "Asia/Ho_Chi_Minh")
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm",timezone = "Asia/Ho_Chi_Minh")
    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public int getPointTranning() {
        return pointTranning;
    }

    public void setPointTranning(int pointTranning) {
        this.pointTranning = pointTranning;
    }

    public int getPointSocial() {
        return pointSocial;
    }

    public void setPointSocial(int pointSocial) {
        this.pointSocial = pointSocial;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }


    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public String getActivityTypeId() {
        return activityTypeId;
    }

    public void setActivityTypeId(String activityTypeId) {
        this.activityTypeId = activityTypeId;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getStatus() {
        Date now = new Date();
        if(now.before(startDate))
            return "Sắp diễn ra";
        else if(now.after(endDate)){
            return "Kết thúc";
        }
        return "Đang diễn ra";
    }

    public void setStatus(String status) {
        this.status = status;
    }
}