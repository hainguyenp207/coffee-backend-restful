package com.coffeeinfinitive.api;

import com.coffeeinfinitive.constants.ResultCode;
import com.coffeeinfinitive.dao.entity.Organization;
import com.coffeeinfinitive.dao.entity.Role;
import com.coffeeinfinitive.dao.entity.Organization;
import com.coffeeinfinitive.exception.CoffeeSystemErrorException;
import com.coffeeinfinitive.model.OrganizationForm;
import com.coffeeinfinitive.service.OrganizationService;
import com.coffeeinfinitive.service.RoleService;
import com.coffeeinfinitive.service.OrganizationService;
import com.coffeeinfinitive.service.ValidatorService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by jinz on 5/2/17.
 * CRUD cho org
 */
@RestController
@RequestMapping(path = "/api/v1/organizations")
public class OrganizationController {

    @Autowired
    OrganizationService organizationService;

    @Autowired
    RoleService roleService;


    @Autowired
    ValidatorService validatorService;

    // Lấy tất cả org
    @GetMapping()
    public ResponseEntity<Iterable<Organization>> getAllOrg(){
        return new ResponseEntity<Iterable<Organization>>(organizationService.getAllOrganization(), HttpStatus.OK);
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<Organization> getOrg(@PathVariable("id") String id){
        Organization org = organizationService.findOrgById(id);
        if(org==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Organization>(org, HttpStatus.OK);
    }
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> addOrganization(@RequestBody OrganizationForm org){
        Organization orgExist = organizationService.findOrgById(org.getId());
        if(orgExist!=null){
            JsonObject result = new JsonObject();
            result.addProperty("code", ResultCode.ORGANZITION_EXIST.getCode());
            result.addProperty("message", ResultCode.ORGANZITION_EXIST.getMessageVn());
            return new ResponseEntity<Object>(result.toString(),HttpStatus.CONFLICT);
        }

        try{
            Organization organization = new Organization();
            organization.setDescription(org.getDescription());
            organization.setName(org.getName());
            organizationService.save(organization);
        }catch (Exception e){
            new CoffeeSystemErrorException("",e);
        }

        return new ResponseEntity<Object>(org, HttpStatus.CREATED);
    }
    @PutMapping(path = "/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> editOrganization(@PathVariable("id") String id, @RequestBody OrganizationForm org){
        Organization orgExist = organizationService.findOrgById(id);
        if(orgExist==null){
            JsonObject result = new JsonObject();
            result.addProperty("code", ResultCode.ORGANZITION_NOT_FOUND.getCode());
            result.addProperty("message", ResultCode.ORGANZITION_NOT_FOUND.getMessageVn());
            return new ResponseEntity<Object>(result.toString(),HttpStatus.NOT_FOUND);
        }

        try{
            if(org.getName()!=null)
                orgExist.setName(org.getName());
            if(org.getDescription()!=null)
                orgExist.setDescription(org.getDescription());
            orgExist = organizationService.update(orgExist);
        }catch (Exception e){
            new CoffeeSystemErrorException("",e);
        }
        return new ResponseEntity<Object>(orgExist,HttpStatus.OK);
    }

}
