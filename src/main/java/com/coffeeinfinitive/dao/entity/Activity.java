package com.coffeeinfinitive.dao.entity;

import com.fasterxml.jackson.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

/**
 * Created by jinz on 4/16/17.
 */
@Entity
@Table(name = "activity")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Activity implements Serializable{

    private String id;
    private String name;
    private String description;
    private Organization organization;
    private String organizationId;
    private String activityTypeId;
    private Date startDate;
    private Date endDate;
    private Date createdDate;
    private Date lastUpdatedDate;
    private User createdBy;
    private User lastUpdatedBy;
    private int pointTranning;
    private int pointSocial;
    private boolean confirmed;
    private boolean isEnabled; // Con thoi gian dang ky
    private TypeActivity typeActivity;
    private Set<Register> registers;
    private Set<Comment> comments;

    public Activity(){
        this.id = UUID.randomUUID().toString();
    }

    public Activity(String name, String description, Organization organization, Date startDate, Date endDate, User createdBy, User lastUpdatedBy, int pointTranning, int pointSocial, TypeActivity typeActivity) {
        this.name = name;
        this.description = description;
        this.organization = organization;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdBy = createdBy;
        this.lastUpdatedBy = lastUpdatedBy;
        this.pointTranning = pointTranning;
        this.pointSocial = pointSocial;
        this.typeActivity = typeActivity;
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
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "activity")
    @JsonManagedReference(value="register")
    public Set<Register> getRegisters() {
        return registers;
    }

    public void setRegisters(Set<Register> registers) {
        this.registers = registers;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false,insertable = false,updatable = false)
    @JsonBackReference(value = "activity-organization")
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Column(name = "start_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "Asia/Ho_Chi_Minh")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "Asia/Ho_Chi_Minh")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "end_date")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Column(name = "created_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "Asia/Ho_Chi_Minh")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    @PrePersist
    public void setCreatedDate() {
        this.createdDate = new Date();
    }

    @Column(name="last_updated_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "Asia/Ho_Chi_Minh")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    @PreUpdate
    public void setLastUpdatedDate() {

        this.lastUpdatedDate =new Date();
    }

    @Column(name = "point_tranning")
    public int getPointTranning() {
        return pointTranning;
    }

    public void setPointTranning(int pointTranning) {
        this.pointTranning = pointTranning;
    }

    @Column(name = "point_social")
    public int getPointSocial() {
        return pointSocial;
    }

    public void setPointSocial(int pointSocial) {
        this.pointSocial = pointSocial;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_type_id", nullable = false,insertable = false,updatable = false)
    @JsonBackReference(value = "type-activity")
    public TypeActivity getTypeActivity() {
        return typeActivity;
    }

    public void setTypeActivity(TypeActivity typeActivity) {
        this.typeActivity = typeActivity;
    }

    @Column(name = "organization_id")
    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    @Column(name = "activity_type_id")
    public String getActivityTypeId() {
        return activityTypeId;
    }

    public void setActivityTypeId(String activityTypeId) {
        this.activityTypeId = activityTypeId;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_updated_by", nullable = false)
    public User getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(User lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    @Transient
    public boolean isEnabled() {
        Date now = new Date();
        return getEndDate().after(now);
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    @OneToMany(mappedBy = "activity")
    @JsonIgnore
    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}
