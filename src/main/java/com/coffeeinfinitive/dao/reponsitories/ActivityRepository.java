package com.coffeeinfinitive.dao.reponsitories;

import com.coffeeinfinitive.dao.entity.Activity;
import com.coffeeinfinitive.dao.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jinz on 4/16/17.
 */
@Repository
public interface ActivityRepository extends JpaRepository<Activity, String>, JpaSpecificationExecutor<Activity> {
//    @Query("select a from Activity a where a.user.username=:userId")
//    List<Activity> getActivitiesByUser(@Param("userId") String userId);
//    List<Activity> getActivitiesByUser(String userId,Pageable pageable);

    @Query("select ac from Activity ac where ac.organization.id=:orgId")
    Page<Activity> getActivitiesByOrg(@Param("orgId") String orgId,Pageable pageable);

    @Query("select ac from Activity ac where ac.confirmed = true ")
    Page<Activity> getActivitiesPublic(Pageable pageable);

    @Query("select count(ac) from Activity ac where ac.confirmed = true")
    long countActivitiesPublic();

    @Query("select count(ac) from Activity ac where ac.confirmed = true and ac.organization.id=:orgId")
    long countActivitiesOrgPublic(@Param("orgId") String orgId);

    @Query("select ac from Activity ac where ac.organization.id=:orgId and ac.confirmed = true order by ac.startDate desc")
    Page<Activity> getActivitiesPublic(@Param("orgId") String orgId,Pageable pageable);

    @Query("select count(r) from Activity r where r.confirmed = false")
    long countActivitiesConfirm();

    @Query("select count(r) from Activity r where r.organization.id=:orgId")
    long countActivitiesByOrg(@Param("orgId") String orgId);

    @Query("select count(r) from Activity r where r.confirmed = false and r.organization.id=:orgId")
    long countActivitiesByOrgConfirm(@Param("orgId") String orgId);

    @Query("select r from Activity r where r.confirmed = true and r.organization.id=:orgId and (r.name LIKE CONCAT('%',:keyword,'%') or r.description LIKE CONCAT('%',:keyword,'%')) order by r.startDate desc" )
    Page<Activity> searchOrg(@Param("keyword") String keyword, @Param("orgId") String orgId,Pageable pageable);

    @Query("select r from Activity r where r.confirmed = true and (r.name LIKE CONCAT('%',:keyword,'%') or r.description LIKE CONCAT('%',:keyword,'%'))")
    Page<Activity> search(@Param("keyword") String keyword,Pageable pageable);
}
