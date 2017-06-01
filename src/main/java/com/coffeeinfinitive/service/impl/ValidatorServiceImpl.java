package com.coffeeinfinitive.service.impl;

import com.coffeeinfinitive.constants.ResultCode;
import com.coffeeinfinitive.dao.entity.Activity;
import com.coffeeinfinitive.dao.entity.Register;
import com.coffeeinfinitive.dao.entity.Role;
import com.coffeeinfinitive.dao.reponsitories.RoleRepository;
import com.coffeeinfinitive.service.RoleService;
import com.coffeeinfinitive.service.ValidatorService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by jinz on 4/16/17.
 */
@Service("validatorService")
public class ValidatorServiceImpl implements ValidatorService{
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public JsonObject validatorRole(List<String> rolesId) {
        JsonArray errors = new JsonArray();
        for(String roleId: rolesId){
            if(roleRepository.findOne(roleId)==null){
                JsonObject error = new JsonObject();
                error.addProperty("param",roleId);
                error.addProperty("message", ResultCode.ROLE_NOT_FOUND.getMessageVn());
                errors.add(error);
            }
        }
        if(errors.size()>0){
            JsonObject resultRole = new JsonObject();
            resultRole.addProperty("code",ResultCode.ROLE_NOT_FOUND.getCode());
            resultRole.addProperty("message","Validate request");
            resultRole.add("detail",errors);
            return resultRole;
        }
        return null;
    }

    @Override
    public ResponseEntity checkExistActivity(Activity activity) {
        if(activity==null){
            JsonObject result = new JsonObject();
            result.addProperty("code", ResultCode.ACTIVITY_NOT_FOUND.getCode());
            result.addProperty("message", ResultCode.ACTIVITY_NOT_FOUND.getMessageVn());
            return new ResponseEntity<>(result.toString(),HttpStatus.NOT_FOUND);
        }else{
            JsonObject result = new JsonObject();
            result.addProperty("code", ResultCode.ACTIVITY_EXIST.getCode());
            result.addProperty("message", ResultCode.ACTIVITY_EXIST.getMessageVn());
            return new ResponseEntity<>(result.toString(),HttpStatus.CONFLICT);
        }
    }

    @Override
    public ResponseEntity checkExistRegister(Register register) {
        if(register==null){
            JsonObject result = new JsonObject();
            result.addProperty("code", ResultCode.REGISTER_NOT_FOUND.getCode());
            result.addProperty("message", ResultCode.REGISTER_NOT_FOUND.getMessageVn());
            return new ResponseEntity<>(result.toString(),HttpStatus.NOT_FOUND);
        }else{
            JsonObject result = new JsonObject();
            result.addProperty("code", ResultCode.REGISTER_EXIST.getCode());
            result.addProperty("message", ResultCode.REGISTER_EXIST.getMessageVn());
            return new ResponseEntity<>(result.toString(),HttpStatus.CONFLICT);
        }
    }

    @Override
    public ResponseEntity<?> validateForm(BindingResult result) {
        JsonObject data = new JsonObject();

        JsonArray errorsJS = new JsonArray();
        if(result.hasErrors()){
            result.getAllErrors().forEach((error)->{
                if(error instanceof FieldError){
                    JsonObject errorJS = new JsonObject();
                    errorJS.addProperty("field", ((FieldError) error).getField());
                    errorJS.addProperty("message", ((FieldError) error).getDefaultMessage());
                    errorsJS.add(errorJS);
                }
            });

        }
        if(errorsJS.size()>0){
            data.addProperty("code", ResultCode.BAD_REQUEST.getCode());
            data.addProperty("message", ResultCode.BAD_REQUEST.getMessageVn());
            data.add("detail", errorsJS);
            return new ResponseEntity<Object>(data.toString(), HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}