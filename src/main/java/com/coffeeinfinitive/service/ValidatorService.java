package com.coffeeinfinitive.service;

import com.coffeeinfinitive.dao.entity.Activity;
import com.coffeeinfinitive.dao.entity.Register;
import com.coffeeinfinitive.dao.entity.Role;
import com.google.gson.JsonObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

/**
 * Created by jinz on 5/2/17.
 */

public interface ValidatorService {
    JsonObject validatorRole(List<String> roles);
    ResponseEntity checkExistActivity(Activity activity);
    ResponseEntity checkExistRegister(Register register);
    ResponseEntity<?> validateForm(BindingResult result);
}
