package com.coffeeinfinitive.dao.reponsitories;

import com.coffeeinfinitive.dao.entity.OrgUser;
import com.coffeeinfinitive.dao.entity.OrgUserId;
import com.coffeeinfinitive.dao.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
   @Query("select p.pk.user from OrgUser p where p.pk.organization.id=:orgId")
   Page<User> getUserByOrg(@Param("orgId") String orgId, Pageable pageable);

   @Query("select count (p.pk.user) from OrgUser p where p.pk.organization.id=:orgId")
   long countOrgUserByOrganization(@Param("orgId") String orgId);
}
