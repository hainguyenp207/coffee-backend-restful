package com.coffeeinfinitive.api;

import com.coffeeinfinitive.Utils;
import com.coffeeinfinitive.constants.ResultCode;
import com.coffeeinfinitive.dao.entity.Activity;
import com.coffeeinfinitive.dao.entity.Register;
import com.coffeeinfinitive.dao.entity.User;
import com.coffeeinfinitive.exception.CoffeeSystemErrorException;
import com.coffeeinfinitive.model.ActivityForm;
import com.coffeeinfinitive.model.RegisterForm;
import com.coffeeinfinitive.service.ActivityService;
import com.coffeeinfinitive.service.RegisterService;
import com.coffeeinfinitive.service.UserService;
import com.coffeeinfinitive.service.ValidatorService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by jinz on 4/15/17.
 * Controller quản lý toàn bộ hoạt động
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/v1/registers")
public class RegisterController {

    @Autowired
    ActivityService activityService;
    @Autowired
    UserService userService;
    @Autowired
    RegisterService registerService;
    @Autowired
    ValidatorService validatorService;
    @GetMapping
    public ResponseEntity<List<Register>> getActivities() {
		  List<Register> registers = registerService.getRegisters();
		  return new ResponseEntity<List<Register>>(registers, HttpStatus.OK);
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<Register> getRegister(@PathVariable("id") String id) {
        Register register = registerService.findRegister(id);
		  if(register==null){
              return new ResponseEntity<>(HttpStatus.NOT_FOUND);
          }
		  return new ResponseEntity<>(register, HttpStatus.OK);
    }
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addRegister(Authentication auth,@RequestBody RegisterForm registerForm) {

        Register currentRegister = registerService.findRegister(registerForm.getId());
        ResponseEntity responseEntity = validatorService.checkExistRegister(currentRegister);
        if(responseEntity.getStatusCode().value()==409){
            return responseEntity;
        }
        Utils<RegisterForm,Register> convert = new Utils<>(Register.class);

        Activity currentActivity = activityService.findActivityById(registerForm.getActivityId());
         responseEntity = validatorService.checkExistActivity(currentActivity);
        if(responseEntity.getStatusCode().value()==404){
            return responseEntity;
        }
        if(registerService.checkUserRegisterActivity(registerForm.getUserId(), registerForm.getActivityId())){
            JsonObject result = new JsonObject();
            result.addProperty("code", ResultCode.REGISTER_EXIST.getCode());
            result.addProperty("message", ResultCode.REGISTER_EXIST.getMessageVn());
            return new ResponseEntity<>(result.toString(),HttpStatus.CONFLICT);
        }

        if(!currentActivity.isConfirmed()){
            JsonObject result = new JsonObject();
            result.addProperty("code", ResultCode.ACTIVITY_NOT_ACTIVED.getCode());
            result.addProperty("message", ResultCode.ACTIVITY_NOT_ACTIVED.getMessageVn());
            return new ResponseEntity<>(result.toString(),HttpStatus.NOT_ACCEPTABLE);
        }
        // Init data;
        User createdBy = userService.findUserById(auth.getPrincipal().toString());
        Register newRegister = convert.ConvertObject(registerForm);

        newRegister.setCreatedDate();
        newRegister.setLastUpdatedDate();
        newRegister.setCreatedBy(createdBy);
        newRegister.setLastUpdatedBy(createdBy);
        Register savedRegister = registerService.save(newRegister);
        return new ResponseEntity<Register>(savedRegister, HttpStatus.CREATED);
    }
    @PutMapping(path = "/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> updateRegister(Authentication auth,
                                            @PathVariable("id") String id,
                                            @RequestBody RegisterForm registerForm) {
        Register currentRegister = registerService.findRegister(registerForm.getId());
        ResponseEntity responseEntity = validatorService.checkExistRegister(currentRegister);
        if(responseEntity.getStatusCode().value()==404){
            return responseEntity;
        }
        Utils<RegisterForm,Register> convert = new Utils<>(Register.class);

        User updatedBy = userService.findUserById(auth.getPrincipal().toString());
        Register newRegister = convert.ConvertObject(registerForm);
        // Init data;
        currentRegister = newRegister;
        currentRegister.setLastUpdatedDate();
        currentRegister.setLastUpdatedBy(updatedBy);
        try {
            currentRegister = registerService.update(currentRegister);
            return new ResponseEntity<>(currentRegister, HttpStatus.OK);
        }catch (Exception e){
            throw new CoffeeSystemErrorException("",e);
        }
    }
    @DeleteMapping(path = "/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> deleteActivity(Authentication auth,@PathVariable("id") String id) {
        Register currentRegister = registerService.findRegister(id);
        if(currentRegister==null){
            JsonObject result = new JsonObject();
            result.addProperty("code", ResultCode.REGISTER_NOT_FOUND.getCode());
            result.addProperty("message", ResultCode.REGISTER_NOT_FOUND.getMessageVn());
            return new ResponseEntity<>(result.toString(),HttpStatus.NOT_FOUND);
        }

        try{
            registerService.delete(id);
            return new ResponseEntity<Activity>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            throw new CoffeeSystemErrorException("",e);
        }
    }

}
