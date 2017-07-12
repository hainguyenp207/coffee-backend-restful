package com.coffeeinfinitive.service;

import com.coffeeinfinitive.dao.entity.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by jinz on 4/16/17.
 */

public interface ActivityService {
    Page<Activity> getActivitiesByPage(Pageable pageable);
    Activity findActivityById(String id);
    Page<Activity> getActivityByUser(String userId,Pageable pageable);
    Page<Activity> getActivityByOrg(String orgId,Pageable pageable);
    Page<Activity> getActivitiesOrgPublic(String orgId,Pageable pageable);
    Page<Activity> search(String keyword, Pageable pageable);
    Page<Activity> searchOrg(String keyword, String orgId, Pageable pageable);
    Page<Activity> getActivityByPublic(Pageable pageable);
    Activity save(Activity activity);
    Activity update(Activity activity);
    long count();
    long countActivitiesConfirm();
    long countActivitiesPubic();
    long countActivitiesOrgPublic(String orgId);
    long countActivitiesByOrg(String orgId);
    long countActivitiesByOrgConfirm(String orgId);
    void delete(String id);
}
