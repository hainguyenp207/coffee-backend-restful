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

    @Override
    public Page<Activity> getActivityByUser(String userId, Pageable pageable) {
        return null;
    }

    @Override
    public Page<Activity> getActivityByOrg(String orgId, Pageable pageable) {
        return activityRepository.getActivitiesByOrg(orgId,pageable);
    }

    @Override
    public long countActivitiesConfirm() {
        return activityRepository.countActivitiesConfirm();
    }

    @Override
    public long countActivitiesByOrg(String orgId) {
        return activityRepository.countActivitiesByOrg(orgId);
    }

    @Override
    public long countActivitiesByOrgConfirm(String orgId) {
        return activityRepository.countActivitiesByOrgConfirm(orgId);
    }

    @Override
    public Page<Activity> getActivitiesOrgPublic(String orgId, Pageable pageable) {
        return activityRepository.getActivitiesPublic(orgId, pageable);
    }

    @Override
    public Page<Activity> getActivityByPublic(Pageable pageable) {
        return activityRepository.getActivitiesPublic(pageable);
    }

    @Override
    public long countActivitiesPubic() {
        return activityRepository.countActivitiesPublic();
    }

    @Override
    public long countActivitiesOrgPublic(String orgId) {
        return activityRepository.countActivitiesOrgPublic(orgId);
    }

    @Override
    public Page<Activity> search(String keyword, Pageable pageable) {
        return activityRepository.search(keyword, pageable);
    }

    @Override
    public Page<Activity> searchOrg(String keyword, String orgId, Pageable pageable) {
        return activityRepository.searchOrg(keyword, orgId, pageable);
    }
}