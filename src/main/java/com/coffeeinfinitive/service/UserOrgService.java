package com.coffeeinfinitive.service;

import com.coffeeinfinitive.dao.entity.OrgUser;
import com.coffeeinfinitive.dao.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

/**
 * Created by jinz on 4/16/17.
 */

public interface UserOrgService {
    Set<OrgUser> getUserOrgByUsername(String id);
}
