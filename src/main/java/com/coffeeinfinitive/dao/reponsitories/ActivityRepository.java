package com.coffeeinfinitive.dao.reponsitories;

import com.coffeeinfinitive.dao.entity.Activity;
import com.coffeeinfinitive.dao.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by jinz on 4/16/17.
 */
@Repository
public interface ActivityRepository extends JpaRepository<Activity, String> {
}
