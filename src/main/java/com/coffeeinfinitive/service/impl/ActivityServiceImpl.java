package com.coffeeinfinitive.service.impl;

import com.coffeeinfinitive.dao.entity.Activity;
import com.coffeeinfinitive.dao.entity.Organization;
import com.coffeeinfinitive.dao.reponsitories.ActivityRepository;
import com.coffeeinfinitive.dao.reponsitories.OrganizationRepository;
import com.coffeeinfinitive.service.ActivityService;
import com.coffeeinfinitive.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by jinz on 4/16/17.
 */
@Service("activityService")
@Transactional
public class ActivityServiceImpl implements ActivityService{
    @Autowired
    private ActivityRepository activityRepository;

    @Override
    public List<Activity> getActivities() {
        return activityRepository.findAll();
    }

    @Override
    public Activity findActivityById(String id) {
        return activityRepository.findOne(id);
    }

    @Override
    public long count() {
        return activityRepository.count();
    }

    @Override
    public Page<Activity> getActivitiesByPage(Pageable pageable) {
        return activityRepository.findAll(pageable);
    }

    @Override
    public Activity save(Activity activity) {
        return activityRepository.save(activity);
    }

    @Override
    public Activity update(Activity activity) {
        return activityRepository.save(activity);
    }

    @Override
    public void delete(String id) {
        activityRepository.delete(id);
    }
}