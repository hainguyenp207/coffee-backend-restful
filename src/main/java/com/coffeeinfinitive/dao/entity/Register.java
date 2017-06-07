package com.coffeeinfinitive.dao.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

/**
 * Created by jinz on 4/27/17.
 * Bảng đăng ký hoạt động của user
 */
@Entity
@Table(name = "register")
public class Register {
    private String id;
    private User user;
    private String userId;
    private boolean isJoined; // Diem danh
    private int pointTranning;
    private int pointSocial;
    private Activity activity;
    private String activityId;
    private Date createdDate;
    private User createdBy;
    private Date lastUpdatedDate;
    private User lastUpdatedBy;

    public Register(String name){
        this.id = UUID.randomUUID().toString();
        this.createdDate = this.lastUpdatedDate = new Date();
    }
    public Register(){
        this.id = UUID.randomUUID().toString();
    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false,insertable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id", nullable = false,updatable = false, insertable = false)
    @JsonManagedReference(value="register")
    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Column(name ="activity_id")
    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    @Column(name = "created_date")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    public void setCreatedDate() {
        this.createdDate = new Date();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
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
    @Column(name = "last_updated_date")
    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
    public void setLastUpdatedDate() {
        this.lastUpdatedDate = new Date();
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_updated_by", nullable = false)
    public User getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(User lastUpdateBy) {
        this.lastUpdatedBy = lastUpdateBy;
    }

    @Column(name = "is_joined")
    public boolean isJoined() {
        return isJoined;
    }

    public void setJoined(boolean joined) {
        isJoined = joined;
    }
}
