package com.coffeeinfinitive.service;

import com.coffeeinfinitive.dao.entity.Organization;
import com.coffeeinfinitive.dao.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by jinz on 4/16/17.
 */

public interface OrganizationService {
    Iterable<Organization> getAllOrganization();
    Page<Organization> getOrganizationsByPage(Pageable pageable);
    long count();
    Organization findOrgById(String id);
    Organization save(Organization organization);
    Organization update(Organization organization);
}
