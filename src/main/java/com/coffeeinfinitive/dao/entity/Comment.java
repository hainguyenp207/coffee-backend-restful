package com.coffeeinfinitive.dao.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "comment")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Comment implements Serializable{

    private String id;
    private String content;
    private String userId;
    private Activity activity;
    private String activityId;
    private User owner;
    private Date createdDate;
    private Date lastUpdatedDate;
    private User lastUpdatedBy;


    public Comment(){
        this.id = UUID.randomUUID().toString();
    }


    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id", nullable = false, insertable = false, updatable = false)
    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
    @Column(name = "activity_id")
    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_updated_by", nullable = false)
    public User getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(User lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
}
