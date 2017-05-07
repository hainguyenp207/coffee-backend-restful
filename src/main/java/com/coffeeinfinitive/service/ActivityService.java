package com.coffeeinfinitive.service;

import com.coffeeinfinitive.dao.entity.Activity;

import java.util.List;

/**
 * Created by jinz on 4/16/17.
 */

public interface ActivityService {
    List<Activity> getActivities();
    Activity findActivityById(String id);
    Activity save(Activity activity);
    Activity update(Activity activity);
    void delete(String id);
}
