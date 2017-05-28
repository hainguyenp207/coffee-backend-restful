package com.coffeeinfinitive.service.impl;

import com.coffeeinfinitive.dao.entity.OrgUser;
import com.coffeeinfinitive.dao.entity.User;
import com.coffeeinfinitive.dao.reponsitories.UserOrgRepository;
import com.coffeeinfinitive.service.UserOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Created by jinz on 5/28/17.
 */
@Service("userOrgService")
@Transactional
public class UserOrgServiceImpl implements UserOrgService {

    @Autowired
    UserOrgRepository userOrgRepository;
    @Override
    public Set<OrgUser> getUserOrgByUsername(String id) {
        return userOrgRepository.findUserOrgByUsername(id);
    }
}
