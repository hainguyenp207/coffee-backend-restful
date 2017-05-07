package com.coffeeinfinitive.model;

import com.coffeeinfinitive.dao.entity.*;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

/**
 * Created by jinz on 4/15/17.
 */

public class RegisterForm {
    private String id;
    private String userId;
    private boolean isJoined; // Diem danh
    private int pointTranning;
    private int pointSocial;
    private String activityId;



    public RegisterForm() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isJoined() {
        return isJoined;
    }

    public void setJoined(boolean joined) {
        isJoined = joined;
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

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }
}
