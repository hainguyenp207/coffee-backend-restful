package com.coffeeinfinitive.api;

import com.coffeeinfinitive.Utils;
import com.coffeeinfinitive.constants.ResultCode;
import com.coffeeinfinitive.dao.entity.*;
import com.coffeeinfinitive.exception.CoffeeSystemErrorException;
import com.coffeeinfinitive.model.ActivityForm;
import com.coffeeinfinitive.model.UserForm;
import com.coffeeinfinitive.service.*;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by jinz on 4/15/17.
 * Controller quản lý toàn bộ hoạt động
 */
@RestController
@RequestMapping(path = "/api/v1/activities")
public class ActivityController {

    @Autowired
    ActivityService activityService;
    @Autowired
    UserService userService;
    @Autowired
    RegisterService registerService;
    @Autowired
    ValidatorService validatorService;
    @Autowired
    OrganizationService organizationService;
    @Autowired
    CommentService commentService;

//    @GetMapping
//    public ResponseEntity<List<?>> getActivities() {
//        List<Activity> activities = activityService.getActivities();
//        List<ActivityForm> activityForms = new ArrayList<>();
//        activities.forEach(activity -> {
//            ActivityForm activityForm = new ActivityForm();
//            activityForm.setConfirmed(activity.isConfirmed());
//            activityForm.setActivityTypeId(activity.getActivityTypeId());
//            activityForm.setStartDate(activity.getStartDate());
//            activityForm.setEndDate(activity.getEndDate());
//            activityForm.setCreatedDate(activity.getCreatedDate());
//            activityForm.setId(activity.getId());
//            activityForm.setName(activity.getName());
//            activityForms.add(activityForm);
//        });
//
//        return new ResponseEntity<List<?>>(activityForms, HttpStatus.OK);
//    }
    @GetMapping
    public List<ActivityForm> getActivitiesByPage(Pageable pageable) {

        Page<Activity> activities = activityService.getActivitiesByPage(pageable);
        List<ActivityForm> activityForms = new ArrayList<>();
        activities.forEach(activity -> {
            ActivityForm activityForm = new ActivityForm();
            activityForm.setConfirmed(activity.isConfirmed());
            activityForm.setActivityTypeId(activity.getActivityTypeId());
            activityForm.setOrganization(activity.getOrganization());
            activityForm.setStartDate(activity.getStartDate());
            activityForm.setEndDate(activity.getEndDate());
            activityForm.setCreatedDate(activity.getCreatedDate());
            activityForm.setId(activity.getId());
            activityForm.setName(activity.getName());
            activityForms.add(activityForm);
        });

        return activityForms;
    }
    @GetMapping("/count")
    public ResponseEntity<?> getTotalRow(){
        return new ResponseEntity<Object>(activityService.count(), HttpStatus.OK);
    }

    /*
    Lay danh sach dang ky theo hoat dong
     */
    @GetMapping(path = "/{id}/registers")
    public ResponseEntity<?> getRegisters(@PathVariable("id") String id) {

        Activity activity = activityService.findActivityById(id);
        if (activity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Set<Register> registers = activity.getRegisters();
        return new ResponseEntity<>(registers, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}/registers/{idRegister}")
    public ResponseEntity<?> getRegister(@PathVariable("id") String id,
                                         @PathVariable("idRegister") String idRegister) {
        Activity activity = activityService.findActivityById(id);

        Register register = registerService.findRegister(idRegister);
        if (register == null) {
            JsonObject result = new JsonObject();
            result.addProperty("code", ResultCode.REGISTER_NOT_FOUND.getCode());
            result.addProperty("message", ResultCode.REGISTER_NOT_FOUND.getMessageVn());
            return new ResponseEntity<>(result.toString(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(register, HttpStatus.OK);
    }

    /*
    Lay comment cua 1 hoat dong
     */
    @GetMapping(path = "/{id}/comments")
    public ResponseEntity<?> getComment(@PathVariable("id") String id) {
        Activity activity = activityService.findActivityById(id);

        if (activity == null) {
            JsonObject result = new JsonObject();
            result.addProperty("code", ResultCode.ACTIVITY_NOT_FOUND.getCode());
            result.addProperty("message", ResultCode.ACTIVITY_NOT_FOUND.getMessageVn());
            return new ResponseEntity<>(result.toString(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(activity.getComments(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getActivity(@PathVariable("id") String id) {
        Activity activity = activityService.findActivityById(id);
        if (activity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ActivityForm activityClient = new ActivityForm();
        activityClient.setName(activity.getName());
        activityClient.setDescription(activity.getDescription());
        activityClient.setStartDate(activity.getStartDate());
        activityClient.setEndDate(activity.getEndDate());
        activityClient.setCreatedDate(activity.getCreatedDate());
        activityClient.setId(activity.getId());
        activityClient.setConfirmed(activity.isConfirmed());
        activityClient.setPointSocial(activity.getPointSocial());
        activityClient.setPointTranning(activity.getPointTranning());
        activityClient.setOrganization(activity.getOrganization());
        return new ResponseEntity<>(activityClient, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> addActivity(Authentication auth,
                                         @Valid @RequestBody ActivityForm activityForm,
                                         BindingResult bindingResult) {

        ResponseEntity response = validatorService.validateForm(bindingResult);
        if(response!=null){
            return response;
        }

        Activity currentActivity = activityService.findActivityById(activityForm.getId());
        ResponseEntity responseEntity = validatorService.checkExistActivity(currentActivity);
        if (responseEntity.getStatusCode().value() == 409) {
            return responseEntity;
        }
        JsonObject result = new JsonObject();
        User createdBy = userService.findUserById(auth.getPrincipal().toString());
        Activity newActivity = new Activity();
        // Init data;
        String organzationId = activityForm.getOrganizationId();
        Organization currentOrganization = organizationService.findOrgById(organzationId);
        if(currentOrganization==null){

            result.addProperty("code", ResultCode.ORGANZITION_NOT_FOUND.getCode());
            result.addProperty("message", ResultCode.ORGANZITION_NOT_FOUND.getMessageVn());
            return new ResponseEntity<>(result.toString(), HttpStatus.OK);
        }
        newActivity.setName(activityForm.getName());
        newActivity.setOrganizationId(activityForm.getOrganizationId());
        newActivity.setDescription(activityForm.getDescription());
        newActivity.setStartDate(activityForm.getStartDate());
        newActivity.setEndDate(activityForm.getEndDate());
        newActivity.setActivityTypeId(activityForm.getActivityTypeId());
        newActivity.setConfirmed(false);
        newActivity.setCreatedDate();
        newActivity.setLastUpdatedDate();
        newActivity.setCreatedBy(createdBy);
        newActivity.setLastUpdatedBy(createdBy);
        newActivity.setOrganization(currentOrganization);
        try{
            result.addProperty("code", ResultCode.SUCCESS.getCode());
            result.addProperty("message", ResultCode.SUCCESS.getMessageVn());
            activityService.save(newActivity);
            return new ResponseEntity<>(activityForm, HttpStatus.CREATED);
        }catch (Exception e){
            result.addProperty("code", ResultCode.INTERNAL_SYSTEM_ERROR.getCode());
            result.addProperty("message", e.getLocalizedMessage());
            return new ResponseEntity<>(activityForm, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> updateActivity(Authentication auth, @PathVariable("id") String id, @RequestBody ActivityForm activityForm) {
        Activity currentActivity = activityService.findActivityById(id);
        JsonObject result = new JsonObject();
        if (currentActivity == null) {
            result.addProperty("code", ResultCode.ACTIVITY_NOT_FOUND.getCode());
            result.addProperty("message", ResultCode.ACTIVITY_NOT_FOUND.getMessageVn());
            return new ResponseEntity<>(result.toString(), HttpStatus.NOT_FOUND);
        }
        Organization currentOrganization = organizationService.findOrgById(activityForm.getOrganizationId());
        if(currentOrganization==null){

            result.addProperty("code", ResultCode.ORGANZITION_NOT_FOUND.getCode());
            result.addProperty("message", ResultCode.ORGANZITION_NOT_FOUND.getMessageVn());
            return new ResponseEntity<>(result.toString(), HttpStatus.OK);
        }

        User updatedBy = userService.findUserById(auth.getPrincipal().toString());
        Activity newActivity = new Activity();


        // Init data;
        currentActivity.setName(activityForm.getName());
        currentActivity.setOrganizationId(activityForm.getOrganizationId());
        currentActivity.setDescription(activityForm.getDescription());
        currentActivity.setStartDate(activityForm.getStartDate());
        currentActivity.setEndDate(activityForm.getEndDate());
        currentActivity.setActivityTypeId(activityForm.getActivityTypeId());
        currentActivity.setConfirmed(activityForm.isConfirmed());
        currentActivity.setLastUpdatedDate();
        currentActivity.setLastUpdatedBy(updatedBy);
        try {
            Activity savedActivity = activityService.update(currentActivity);
            result.addProperty("code",ResultCode.SUCCESS.getCode());
            result.addProperty("message",ResultCode.SUCCESS.getMessageVn());
            return new ResponseEntity<>(result.toString(), HttpStatus.OK);
        } catch (Exception e) {
            throw new CoffeeSystemErrorException("", e);
        }
    }

    @DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> deleteActivity(Authentication auth, @PathVariable("id") String id) {
        Activity currentActivity = activityService.findActivityById(id);
        if (currentActivity == null) {
            JsonObject result = new JsonObject();
            result.addProperty("code", ResultCode.ACTIVITY_NOT_FOUND.getCode());
            result.addProperty("message", ResultCode.ACTIVITY_NOT_FOUND.getMessageVn());
            return new ResponseEntity<>(result.toString(), HttpStatus.NOT_FOUND);
        }

        try {
            activityService.delete(id);
            return new ResponseEntity<Activity>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            throw new CoffeeSystemErrorException("", e);
        }
    }

}
