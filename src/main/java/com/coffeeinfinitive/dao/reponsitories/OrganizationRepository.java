package com.coffeeinfinitive.dao.reponsitories;

import com.coffeeinfinitive.dao.entity.Organization;
import com.coffeeinfinitive.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

/**
 * Created by jinz on 4/16/17.
 */
@RepositoryRestResource
public interface OrganizationRepository extends PagingAndSortingRepository<Organization, String> {
}
