package com.coffeeinfinitive.api;

import com.coffeeinfinitive.Utils;
import com.coffeeinfinitive.constants.ResultCode;
import com.coffeeinfinitive.dao.entity.OrgUser;
import com.coffeeinfinitive.dao.entity.Organization;
import com.coffeeinfinitive.dao.entity.Role;
import com.coffeeinfinitive.dao.entity.User;
import com.coffeeinfinitive.model.*;
import com.coffeeinfinitive.service.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
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
    UserOrgService userOrgService;

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

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getUser(@PathVariable("id") String id){
        User user = userService.findUserById(id);
        if(user==null){
            JsonObject res = new JsonObject();
            res.addProperty("code", ResultCode.USER_NOT_FOUND.getCode());
            res.addProperty("message", ResultCode.USER_NOT_FOUND.getMessageVn());
            return new ResponseEntity<>(res.toString(),HttpStatus.NOT_FOUND);
        }
        UserForm userClient = new UserForm();
        UserForm userForm = new UserForm();
        userForm.setName(user.getName());

        Set<UserOrgDetailForm> userOrgDetailForms = new HashSet<>();
        Set<OrgUser> orgUsers = userOrgService.getUserOrgByUsername(id);
        orgUsers.forEach(orgUser -> {
            OrganizationForm organizationForm = new OrganizationForm(false,orgUser.getOrganization().getId(), orgUser.getOrganization().getName());
            RoleForm roleForm = new RoleForm(orgUser.getRole().getId(), orgUser.getRole().getName());
            UserOrgDetailForm userOrgDetailForm = new UserOrgDetailForm(organizationForm,roleForm);
            userOrgDetailForms.add(userOrgDetailForm);
        });

        userForm.setUserOrgDetailForms(userOrgDetailForms);
        userForm.setEmail(user.getEmail());
        userForm.setSex(user.isSex());
        userForm.setUsername(user.getUsername());
        userForm.setAddress(user.getAddress());
        Gson gson = new Gson();
        String body = gson.toJson(userForm,userForm.getClass());
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> addUser(@RequestBody UserForm userForm){
        User newUser = new User();
        User userExist = userService.findUserById(userForm.getUsername());

        if(userExist!=null){
            JsonObject result = new JsonObject();
            result.addProperty("code", ResultCode.USER_EXIST.getCode());
            result.addProperty("message", ResultCode.USER_EXIST.getMessageVn());
            return new ResponseEntity<Object>(result.toString(),HttpStatus.CONFLICT);
        }

        Set<UserOrgForm> userOrgForms = userForm.getUserOrgForm();
        newUser.setUsername(userForm.getUsername());
        newUser.setEmail(userForm.getEmail());
        newUser.setAddress(userForm.getAddress());
        newUser.setName(userForm.getName());
        newUser.setPassword(passwordEncoder.encode(userForm.getPassword()));
        Set<OrgUser> orgUsers = new HashSet<>();
        User finalNewUser = newUser;
        userForm.getUserOrgForm().forEach(userOrgForm -> {
            Role role = roleService.findRoleById(userOrgForm.getRoleId());
            Organization org = orgService.findOrgById(userOrgForm.getOrganizationId());
            OrgUser orgUser = new OrgUser();
            orgUser.setOrganization(org);
            orgUser.setRole(role);
            orgUser.setUser(finalNewUser);
            orgUsers.add(orgUser);
        });

        try{
            newUser = userService.save(finalNewUser);
            userOrgService.save(orgUsers);
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
    public ResponseEntity<?> editUser(@PathVariable("id") String id, @RequestBody UserForm userForm){
        User userExist = userService.findUserById(id);
        userForm.setUsername(id);
        if(userExist==null){
            JsonObject result = new JsonObject();
            result.addProperty("code", ResultCode.USER_NOT_FOUND.getCode());
            result.addProperty("message", ResultCode.USER_NOT_FOUND.getMessageVn());
            return new ResponseEntity<Object>(result.toString(),HttpStatus.NOT_FOUND);
        }

        Set<UserOrgForm> userOrgForms = userForm.getUserOrgForm();
        userExist.setEmail(userForm.getEmail());
        userExist.setAddress(userForm.getAddress());
        userExist.setName(userForm.getName());

        if(userForm.getPassword()!=null)
        userForm.setPassword(passwordEncoder.encode(userForm.getPassword()));

        Set<OrgUser> orgUsers = new HashSet<>();

        userForm.getUserOrgForm().forEach(userOrgForm -> {
            Role role = roleService.findRoleById(userOrgForm.getRoleId());
            Organization org = orgService.findOrgById(userOrgForm.getOrganizationId());
            OrgUser orgUser = new OrgUser();
            orgUser.setOrganization(org);
            orgUser.setRole(role);
            orgUser.setUser(userExist);
            orgUsers.add(orgUser);

        });

        try{
            userService.updateUser(userExist);
            userOrgService.save(orgUsers);
        }catch (Exception e){
            JsonObject result = new JsonObject();
            result.addProperty("code", ResultCode.INTERNAL_SYSTEM_ERROR.getCode());
            result.addProperty("message", ResultCode.INTERNAL_SYSTEM_ERROR.getMessageVn());
            return new ResponseEntity<Object>(result.toString(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Object>(userForm,HttpStatus.OK);
    }

}
