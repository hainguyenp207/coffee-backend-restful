package com.coffeeinfinitive.service;

import com.coffeeinfinitive.dao.entity.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by jinz on 4/16/17.
 */

public interface ActivityService {
    List<Activity> getActivities() ;
    Page<Activity> getActivitiesByPage(Pageable pageable);
    Activity findActivityById(String id);
    Activity save(Activity activity);
    Activity update(Activity activity);
    long count();
    void delete(String id);
}
