package com.coffeeinfinitive.dao.reponsitories;

import com.coffeeinfinitive.dao.entity.Activity;
import com.coffeeinfinitive.dao.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jinz on 4/16/17.
 */
@Repository
public interface ActivityRepository extends JpaRepository<Activity, String> {
//    @Query("select a from Activity a where a.user")
//    List<Activity> getActivitiesByUser()
}
