package com.coffeeinfinitive.dao.reponsitories;

import com.coffeeinfinitive.dao.enity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by jinz on 4/16/17.
 */
@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    User getAllUser();
    User findByAgent(String agent);
}
