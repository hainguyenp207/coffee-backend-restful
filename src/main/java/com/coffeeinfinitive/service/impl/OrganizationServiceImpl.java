package com.coffeeinfinitive.service.impl;

import com.coffeeinfinitive.dao.entity.Organization;
import com.coffeeinfinitive.dao.entity.Role;
import com.coffeeinfinitive.dao.reponsitories.OrganizationRepository;
import com.coffeeinfinitive.dao.reponsitories.RoleRepository;
import com.coffeeinfinitive.service.OrganizationService;
import com.coffeeinfinitive.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by jinz on 4/16/17.
 */
@Service("organizationService")
@Transactional
public class OrganizationServiceImpl implements OrganizationService{
    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public Iterable<Organization> getAllOrganization() {
        return organizationRepository.findAll();
    }

    @Override
    public Page<Organization> getOrganizationsByPage(Pageable pageable) {
        return organizationRepository.findAll(pageable);
    }

    @Override
    public long count() {
        return organizationRepository.count();
    }

    @Override
    public Organization findOrgById(String id) {
        return organizationRepository.findOne(id);
    }

    @Override
    public Organization save(Organization organization) {
        return organizationRepository.save(organization);
    }

    @Override
    public Organization update(Organization organization) {
        return organizationRepository.save(organization);
    }
}