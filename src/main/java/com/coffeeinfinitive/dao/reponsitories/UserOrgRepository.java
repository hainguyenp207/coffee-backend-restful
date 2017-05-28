package com.coffeeinfinitive.dao.reponsitories;

import com.coffeeinfinitive.dao.entity.OrgUser;
import com.coffeeinfinitive.dao.entity.OrgUserId;
import com.coffeeinfinitive.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Created by jinz on 4/16/17.
 */
@Repository
public interface UserOrgRepository extends JpaRepository<OrgUser, OrgUserId> {
   @Query("select p from OrgUser p where p.pk.user.username=:username")
   Set<OrgUser> findUserOrgByUsername(@Param("username") String username);
}
