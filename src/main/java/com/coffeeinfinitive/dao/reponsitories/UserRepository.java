package com.coffeeinfinitive.dao.reponsitories;

import com.coffeeinfinitive.dao.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by jinz on 4/16/17.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
   @Query("select p from User p where p.username=:username")
   User findUserByUsername(@Param("username") String username);
//   @Query("select p from User p where p.orgUsers=:username")
//   Page<User> getUsersByOrg(@Param("username") String username);
}
