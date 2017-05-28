package com.coffeeinfinitive.api;

import com.coffeeinfinitive.Utils;
import com.coffeeinfinitive.constants.ResultCode;
import com.coffeeinfinitive.dao.entity.Role;
import com.coffeeinfinitive.dao.entity.User;
import com.coffeeinfinitive.model.UserForm;
import com.coffeeinfinitive.model.UserOrgForm;
import com.coffeeinfinitive.service.OrganizationService;
import com.coffeeinfinitive.service.RoleService;
import com.coffeeinfinitive.service.UserService;
import com.coffeeinfinitive.service.ValidatorService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by jinz on 5/2/17.
 * CRUD cho user
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;
    @Autowired
    OrganizationService orgService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ValidatorService validatorService;


    @GetMapping("/count")
    public ResponseEntity<?> getTotalRow(){
     return new ResponseEntity<Object>(userService.count(), HttpStatus.OK);
    }
    // Lấy tất cả user
    @GetMapping()
    public ResponseEntity<List<?>> getAllUser(){
        List<User> users = userService.getAllUser();
        List<UserForm> usersModel = new ArrayList<>();
        users.forEach(user->{
            UserForm userForm = new UserForm(user.getUsername(),user.getName(),
                    user.getEmail(),user.getNumber(), user.isSex(),
                    user.getAddress(),null );
            usersModel.add(userForm);
        });
        return new ResponseEntity<List<?>>(usersModel, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") String id){
        User user = userService.findUserById(id);
        if(user==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserForm userClient = new UserForm();
        userClient.setName(user.getName());
        userClient.setAddress(user.getAddress());
        userClient.setUsername(user.getUsername());
        return new ResponseEntity<>(userClient, HttpStatus.OK);
    }
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> addUser(@RequestBody UserForm userForm){

        Utils<UserForm,User> convert = new Utils<UserForm,User>(User.class);

        User newUser = convert.ConvertObject(userForm);
        User userExist = userService.findUserById(userForm.getUsername());

        if(userExist!=null){
            JsonObject result = new JsonObject();
            result.addProperty("code", ResultCode.USER_EXIST.getCode());
            result.addProperty("message", ResultCode.USER_EXIST.getMessageVn());
            return new ResponseEntity<Object>(result.toString(),HttpStatus.CONFLICT);
        }

        Set<UserOrgForm> userOrgForms = userForm.getUserOrgForm();

        if(userOrgForms !=null && !userOrgForms.isEmpty()){
            List<String> roleIds = new ArrayList<>();
            userOrgForms.forEach(userOrgForm -> {
                roleIds.add(userOrgForm.getRoleId());
            });
            JsonObject validateRole = validatorService.validatorRole(roleIds);
            if(validateRole!=null){
                return new ResponseEntity<Object>(validateRole.toString(), HttpStatus.BAD_REQUEST);
            }
        }
        if(orgService.findOrgById(newUser.getOrganizationId())==null){
            JsonObject result = new JsonObject();
            result.addProperty("code", ResultCode.ORGANZITION_NOT_FOUND.getCode());
            result.addProperty("message", ResultCode.ORGANZITION_NOT_FOUND.getMessageVn());
            return new ResponseEntity<Object>(result.toString(),HttpStatus.BAD_REQUEST);
        }


        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        try{
            newUser = userService.save(newUser);
        }catch (Exception e){
            e.printStackTrace();
            JsonObject result = new JsonObject();
            result.addProperty("code", ResultCode.INTERNAL_SYSTEM_ERROR.getCode());
            result.addProperty("message", ResultCode.INTERNAL_SYSTEM_ERROR.getMessageVn());
            return new ResponseEntity<Object>(result.toString(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Object>(newUser, HttpStatus.CREATED);
    }
    @PutMapping(path = "/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> editUser(@PathVariable("id") String id, @RequestBody User user){
        User userExist = userService.findUserById(id);
        user.setUsername(id);
        if(userExist==null){
            JsonObject result = new JsonObject();
            result.addProperty("code", ResultCode.USER_NOT_FOUND.getCode());
            result.addProperty("message", ResultCode.USER_NOT_FOUND.getMessageVn());
            return new ResponseEntity<Object>(result.toString(),HttpStatus.CONFLICT);
        }
//        Set<Role> roles = user.getRoles();
//        if(roles !=null && !roles.isEmpty()){
//            List<Role> roleList =  new ArrayList<>();
//            roleList.addAll(roles);
//            JsonObject validateRole = validatorService.validatorRole(roleList);
//            if(validateRole!=null){
//                return new ResponseEntity<Object>(validateRole.toString(), HttpStatus.BAD_REQUEST);
//            }
//        }

        if(user.getPassword()!=null || !user.getPassword().isEmpty())
            user.setPassword(passwordEncoder.encode(user.getPassword()));

        try{
            userService.updateUser(user);
        }catch (Exception e){
            JsonObject result = new JsonObject();
            result.addProperty("code", ResultCode.INTERNAL_SYSTEM_ERROR.getCode());
            result.addProperty("message", ResultCode.INTERNAL_SYSTEM_ERROR.getMessageVn());
            return new ResponseEntity<Object>(result.toString(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Object>(user,HttpStatus.OK);
    }

}
