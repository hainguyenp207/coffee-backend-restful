package com.coffeeinfinitive.model;

/**
 * Created by jinz on 5/6/17.
 */
public class CommentForm {
    private String content;
    private String activityId;

    CommentForm(){

    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }
}
