package com.coffeeinfinitive.api;

import com.coffeeinfinitive.constants.ResultCode;
import com.coffeeinfinitive.dao.entity.*;
import com.coffeeinfinitive.exception.CoffeeSystemErrorException;
import com.coffeeinfinitive.model.ActivityForm;
import com.coffeeinfinitive.service.*;
import com.coffeeinfinitive.storage.StorageService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by jinz on 4/15/17.
 * Controller quản lý toàn bộ hoạt động
 */
@RestController
@CrossOrigin(origins = "**")
@RequestMapping(path = "/api/v1/activities",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
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

    private Gson gson;

    @Value("${server.context-path}")
    private static String UPLOADED_FOLDER;
    private final StorageService storageService;

    @Autowired
    public ActivityController(StorageService storageService) {
        this.storageService = storageService;
        this.UPLOADED_FOLDER = "";
        gson = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm").create();
    }
    @GetMapping()
    public List<ActivityForm> getActivities(@RequestParam(value = "page",defaultValue = "0") int page,
                                                  @RequestParam(value = "size", defaultValue = "50") int size) {
        PageRequest pageRequest = new PageRequest(page,size);
        Page<Activity> activities = activityService.getActivitiesByPage(pageRequest);
        List<ActivityForm> activityForms = new ArrayList<>();
        activities.forEach(activity -> {
            ActivityForm activityForm = new ActivityForm();
            activityForm.setConfirmed(activity.isConfirmed());
            activityForm.setDescription(activity.getDescription());
            activityForm.setActivityTypeId(activity.getActivityTypeId());
            activityForm.setOrganization(activity.getOrganization());
            activityForm.setOrganizationId(activity.getOrganizationId());
            activityForm.setStartDate(activity.getStartDate());
            activityForm.setEndDate(activity.getEndDate());
            activityForm.setCreatedDate(activity.getCreatedDate());
            activityForm.setId(activity.getId());
            activityForm.setName(activity.getName());
            activityForm.setImgUrl(activity.getImg());
            activityForms.add(activityForm);
        });
        return activityForms;
    }

    @GetMapping(path = "/public")
    public List<ActivityForm> getActivitiesPublic(@RequestParam(value = "page",defaultValue = "0") int page,
                                                  @RequestParam(value = "size", defaultValue = "50") int size) {
        PageRequest pageRequest = new PageRequest(page,size);
        Page<Activity> activities = activityService.getActivityByPublic(pageRequest);
        List<ActivityForm> activityForms = new ArrayList<>();
        activities.forEach(activity -> {
            ActivityForm activityForm = new ActivityForm();
            activityForm.setConfirmed(activity.isConfirmed());
            activityForm.setImgUrl(activity.getImg());
            activityForm.setActivityTypeId(activity.getActivityTypeId());
            activityForm.setOrganization(activity.getOrganization());
            activityForm.setOrganizationId(activity.getOrganizationId());
            activityForm.setStartDate(activity.getStartDate());
            activityForm.setEndDate(activity.getEndDate());
            activityForm.setCreatedDate(activity.getCreatedDate());
            activityForm.setId(activity.getId());
            activityForm.setName(activity.getName());
            activityForms.add(activityForm);
        });
        return activityForms;
    }

    @GetMapping(path = "/public/search")
    public List<ActivityForm> searchActivitiesPublic(
            @RequestParam(value = "q") String keyword,
            @RequestParam(value = "organization_id", required = false) String organizationId, @RequestParam(value = "page",defaultValue = "0") int page,
                                                        @RequestParam(value = "size", defaultValue = "50") int size) {
        PageRequest pageRequest = new PageRequest(page,size);
        Page<Activity> activities;
        if(organizationId==null){
            if(keyword.isEmpty())
                activities = activityService.getActivityByPublic(pageRequest);
            else
                activities = activityService.search(keyword,pageRequest);
        }
        else{
            if(keyword.isEmpty())
                activities = activityService.getActivitiesOrgPublic(organizationId,pageRequest);
            else
            activities = activityService.searchOrg(keyword, organizationId, pageRequest);
        }

        List<ActivityForm> activityForms = new ArrayList<>();
        activities.forEach(activity -> {
            ActivityForm activityForm = new ActivityForm();
            activityForm.setImgUrl(activity.getImg());
            activityForm.setConfirmed(activity.isConfirmed());
            activityForm.setActivityTypeId(activity.getActivityTypeId());
            activityForm.setOrganization(activity.getOrganization());
            activityForm.setOrganizationId(activity.getOrganizationId());
            activityForm.setStartDate(activity.getStartDate());
            activityForm.setEndDate(activity.getEndDate());
            activityForm.setCreatedDate(activity.getCreatedDate());
            activityForm.setId(activity.getId());
            activityForm.setName(activity.getName());

            activityForms.add(activityForm);
        });
        return activityForms;
    }

    @GetMapping(path = "/organizations/{orgId}/public")
    public List<ActivityForm> getActivitiesPublicOrg(@PathVariable("orgId") String orgId, @RequestParam(value = "page",defaultValue = "0") int page,
                                                  @RequestParam(value = "size", defaultValue = "50") int size) {
        PageRequest pageRequest = new PageRequest(page,size);
        Page<Activity> activities = activityService.getActivitiesOrgPublic(orgId,pageRequest);
        List<ActivityForm> activityForms = new ArrayList<>();
        activities.forEach(activity -> {
            ActivityForm activityForm = new ActivityForm();
            activityForm.setConfirmed(activity.isConfirmed());
            activityForm.setActivityTypeId(activity.getActivityTypeId());
            activityForm.setOrganization(activity.getOrganization());
            activityForm.setOrganizationId(activity.getOrganizationId());
            activityForm.setStartDate(activity.getStartDate());
            activityForm.setImgUrl(activity.getImg());

            activityForm.setEndDate(activity.getEndDate());
            activityForm.setCreatedDate(activity.getCreatedDate());
            activityForm.setId(activity.getId());
            activityForm.setName(activity.getName());
            activityForms.add(activityForm);
        });
        return activityForms;
    }

    @GetMapping(path = "/points")
    public List<ActivityForm> getActivitiesMarkPoint(@RequestParam(value = "page",defaultValue = "0") int page,
                                                  @RequestParam(value = "size", defaultValue = "50") int size) {
        PageRequest pageRequest = new PageRequest(page,size);
        Page<Activity> activities = activityService.getActivitiesByPage(pageRequest);
        List<ActivityForm> activityForms = new ArrayList<>();
        activities.forEach(activity -> {
            ActivityForm activityForm = new ActivityForm();
            activityForm.setConfirmed(activity.isConfirmed());
            activityForm.setActivityTypeId(activity.getActivityTypeId());
            activityForm.setOrganization(activity.getOrganization());
            activityForm.setOrganizationId(activity.getOrganizationId());
            activityForm.setStartDate(activity.getStartDate());
            activityForm.setEndDate(activity.getEndDate());
            activityForm.setCreatedDate(activity.getCreatedDate());
            activityForm.setId(activity.getId());
            activityForm.setImgUrl(activity.getImg());
            activityForm.setName(activity.getName());
            activityForm.setCountRegistered(registerService.getRegisteredOfActivity(activity.getId()));
            activityForms.add(activityForm);
        });

        return activityForms;
    }

    @GetMapping(path="/count")
    public ResponseEntity<?> getTotalRow(@RequestParam(value = "org", required = false) String org){
        if(org == null)
            return new ResponseEntity<Object>(activityService.count(), HttpStatus.OK);
        return new ResponseEntity<Object>(activityService.countActivitiesByOrg(org),HttpStatus.OK);
    }

    /**
     * Paging public
     * @param organization
     * @return
     */
    @GetMapping(path="/count/public")
    public ResponseEntity<?> countActivitiesPublic(@RequestParam(value = "org", required = false) String organization){
        if(organization == null)
        return new ResponseEntity<Object>(activityService.countActivitiesPubic(), HttpStatus.OK);
        return new ResponseEntity<Object>(activityService.countActivitiesOrgPublic(organization),HttpStatus.OK);
    }

    // Đếm tổng số hoạt động chờ duyệt
    @GetMapping(path = "/count/confirm")
    public ResponseEntity<?> getTotalActivitiesConfirm(@RequestParam(value = "org", required = false) String org){
        if(org == null
                || org.isEmpty())
        return new ResponseEntity<Object>(activityService.countActivitiesConfirm(),
                HttpStatus.OK);
        return new ResponseEntity<Object>(activityService.countActivitiesByOrgConfirm(org),HttpStatus.OK);

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
        activityClient.setImgUrl(activity.getImg());
        activityClient.setConfirmed(activity.isConfirmed());
        activityClient.setPointSocial(activity.getPointSocial());
        activityClient.setPointTranning(activity.getPointTranning());
        activityClient.setOrganization(activity.getOrganization());
        return new ResponseEntity<>(activityClient, HttpStatus.OK);
    }

    // Lấy hoạt động theo user
    @GetMapping(path = "/user/{userId}")
    public ResponseEntity<?> getActivityByUser(@PathVariable("userId") String userId, Pageable pageable) {
        User user = userService.findUserById(userId);
        if(user==null){
            JsonObject result = new JsonObject();
            result.addProperty("code", ResultCode.USER_NOT_FOUND.getCode());
            result.addProperty("message", ResultCode.USER_NOT_FOUND.getMessageVn());
            return new ResponseEntity<Object>(result.toString(),HttpStatus.NOT_FOUND);
        }

        Page<Activity> activities = activityService.getActivityByUser(userId,pageable);
        List<ActivityForm> activityForms = new ArrayList<>();
        activities.forEach(activity -> {
            ActivityForm activityForm = new ActivityForm();
            activityForm.setConfirmed(activity.isConfirmed());
            activityForm.setImgUrl(activity.getImg());
            activityForm.setActivityTypeId(activity.getActivityTypeId());
            activityForm.setOrganization(activity.getOrganization());
            activityForm.setStartDate(activity.getStartDate());
            activityForm.setEndDate(activity.getEndDate());
            activityForm.setImgUrl(activity.getImg());
            activityForm.setCreatedDate(activity.getCreatedDate());
            activityForm.setId(activity.getId());
            activityForm.setName(activity.getName());
            activityForms.add(activityForm);
        });
        return new ResponseEntity<>(activityForms, HttpStatus.OK);
    }

    // Lấy hoạt động theo to chuc
    @GetMapping(path = "/org/{orgId}")
    public ResponseEntity<?> getActivityByOrg(@PathVariable("orgId") String orgId, Pageable pageable) {
        Organization organization = organizationService.findOrgById(orgId);
        if(organization==null){
            JsonObject result = new JsonObject();
            result.addProperty("code", ResultCode.ORGANZITION_NOT_FOUND.getCode());
            result.addProperty("message", ResultCode.ORGANZITION_NOT_FOUND.getMessageVn());
            return new ResponseEntity<Object>(result.toString(),HttpStatus.NOT_FOUND);
        };
        Page<Activity> activities = activityService.getActivityByOrg(orgId,pageable);
        List<ActivityForm> activityForms = new ArrayList<>();
        activities.forEach(activity -> {
            ActivityForm activityForm = new ActivityForm();
            activityForm.setName(activity.getName());
            activityForm.setDescription(activity.getDescription());
            activityForm.setStartDate(activity.getStartDate());
            activityForm.setEndDate(activity.getEndDate());
            activityForm.setCreatedDate(activity.getCreatedDate());
            activityForm.setId(activity.getId());
            activityForm.setConfirmed(activity.isConfirmed());
            activityForm.setPointSocial(activity.getPointSocial());
            activityForm.setPointTranning(activity.getPointTranning());
            activityForm.setOrganization(activity.getOrganization());
            activityForm.setImgUrl(activity.getImg());
            activityForms.add(activityForm);
        });
        return new ResponseEntity<>(activityForms, HttpStatus.OK);
    }

    // Lấy danh sách hoạt động của 1 tổ chức + số lượng đăng ký
    @GetMapping(path = "/org/{orgId}/points")
    public ResponseEntity<?> getActivityOrgPoint(@PathVariable("orgId") String orgId, Pageable pageable) {
        Organization organization = organizationService.findOrgById(orgId);
        if(organization==null){
            JsonObject result = new JsonObject();
            result.addProperty("code", ResultCode.ORGANZITION_NOT_FOUND.getCode());
            result.addProperty("message", ResultCode.ORGANZITION_NOT_FOUND.getMessageVn());
            return new ResponseEntity<Object>(result.toString(),HttpStatus.NOT_FOUND);
        };
        Page<Activity> activities = activityService.getActivitiesOrgPublic(orgId,pageable);
        List<ActivityForm> activityForms = new ArrayList<>();
        activities.forEach(activity -> {
            ActivityForm activityForm = new ActivityForm();
            activityForm.setName(activity.getName());
            activityForm.setImgUrl(activity.getImg());
            activityForm.setDescription(activity.getDescription());
            activityForm.setStartDate(activity.getStartDate());
            activityForm.setEndDate(activity.getEndDate());
            activityForm.setCreatedDate(activity.getCreatedDate());
            activityForm.setId(activity.getId());
            activityForm.setConfirmed(activity.isConfirmed());
            activityForm.setPointSocial(activity.getPointSocial());
            activityForm.setPointTranning(activity.getPointTranning());
            activityForm.setOrganization(activity.getOrganization());
            activityForm.setCountRegistered(registerService.getRegisteredOfActivity(activity.getId()));
            activityForms.add(activityForm);
        });
        return new ResponseEntity<>(activityForms, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<?> addActivity(Authentication auth,
                                         @RequestPart String data,
                                         @RequestPart(required = false) MultipartFile file                                     ) {

        ActivityForm activityForm   = gson.fromJson(data,ActivityForm.class);
//        ResponseEntity response = validatorService.validateForm(bindingResult);
//        if(response!=null){
//            return response;
//        }

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
        newActivity.setPointTranning(activityForm.getPointTranning());
        newActivity.setPointSocial(activityForm.getPointSocial());
        if(file == null){
            newActivity.setImg("hcmute.png");
        }else{
            storageService.store(file);
            newActivity.setImg(file.getOriginalFilename());
        }

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
    public ResponseEntity<?> updateActivity(Authentication auth, @PathVariable("id") String id,
                                            @RequestPart String data,
                                            @RequestPart(required = false) MultipartFile file
                                           ) {
        ActivityForm activityForm   = gson.fromJson(data,ActivityForm.class);
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
        currentActivity.setPointSocial(activityForm.getPointSocial());
        currentActivity.setPointTranning(activityForm.getPointTranning());
        if(file == null){
            newActivity.setImg("hcmute.png");
        }else{
            if(!file.getOriginalFilename().equalsIgnoreCase(currentActivity.getImg())){
                storageService.store(file);
                currentActivity.setImg(file.getOriginalFilename());
            }
        }
        try {
            Activity savedActivity = activityService.update(currentActivity);
            result.addProperty("code",ResultCode.SUCCESS.getCode());
            result.addProperty("message",ResultCode.SUCCESS.getMessageVn());
            return new ResponseEntity<>(result.toString(), HttpStatus.OK);
        } catch (Exception e) {
            throw new CoffeeSystemErrorException("", e);
        }
    }
    @PutMapping(path = "/{id}/form", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> updateActivity(Authentication auth, @PathVariable("id") String id,
                                            @RequestBody ActivityForm activityForm ) {
        Activity currentActivity = activityService.findActivityById(id);
        if (currentActivity == null) {
            JsonObject result = new JsonObject();
            result.addProperty("code", ResultCode.ACTIVITY_NOT_FOUND.getCode());
            result.addProperty("message", ResultCode.ACTIVITY_NOT_FOUND.getMessageVn());
            return new ResponseEntity<>(result.toString(), HttpStatus.NOT_FOUND);
        }
        currentActivity.setConfirmed(activityForm.isConfirmed());
        activityService.update(currentActivity);
        return new ResponseEntity<>(activityForm.isConfirmed(),HttpStatus.OK);    }

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
            JsonObject result = new JsonObject();
            result.addProperty("code", ResultCode.SUCCESS.getCode());
            result.addProperty("message", "Hoạt động đã được xóa");
            return new ResponseEntity<>(result.toString(),HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            throw new CoffeeSystemErrorException("", e);
        }
    }

    private void saveUploadedFiles(MultipartFile file) throws IOException {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

    }

}
