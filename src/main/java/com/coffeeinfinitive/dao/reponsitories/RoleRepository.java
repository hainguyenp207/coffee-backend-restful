package com.coffeeinfinitive.dao.reponsitories;

import com.coffeeinfinitive.dao.entity.Role;
import com.coffeeinfinitive.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by jinz on 4/16/17.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
}
