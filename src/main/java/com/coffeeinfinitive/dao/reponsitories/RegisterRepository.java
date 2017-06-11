package com.coffeeinfinitive.dao.reponsitories;

import com.coffeeinfinitive.dao.entity.Register;
import com.coffeeinfinitive.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jinz on 4/16/17.
 */
@Repository
public interface RegisterRepository extends JpaRepository<Register, String> {
    @Query("select count(r) from Register r where r.userId =:userId and r.activityId = :activityId")
    int checkUserRegisteredActivity(@Param("userId") String userId, @Param("activityId") String activityId);

    @Query("select r from Register r where r.userId =:userId")
    List<Register> getRegistersByUser(@Param("userId") String userId);
    @Query("select count(r) from Register r where r.activityId =:activityId")
    long getRegistersOfActivity(@Param("activityId") String activityId);

}
