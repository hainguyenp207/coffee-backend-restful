package com.mservice.sdk.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mservice.sdk.authentication.MomoAuthenticationProvider;
import com.mservice.sdk.model.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by jinz on 4/15/17.
 */
@Controller

public class LoginController {

    @Autowired
    MomoAuthenticationProvider authenticationProvider;

    @Value("#{servletContext.contextPath}")
    private String servletContextPath;


    @GetMapping (path = "/login")
    public String getLoginPage(Model model) {
        LoginForm loginForm = new LoginForm();
        model.addAttribute("login", loginForm);
        return "login";
    }
    @PostMapping(path = "/abc", produces = "application/json")
    @ResponseBody
    public String loginHandler(@Valid @RequestBody LoginForm loginForm, BindingResult bindingResult){

        JsonObject result = new JsonObject();

        if(bindingResult.hasErrors()){
            result.addProperty("result","failure");
            result.addProperty("message","Validation form");
            JsonArray errors = new JsonArray();
            result.add("errors",errors);
            List<FieldError> fieldErrorList= bindingResult.getFieldErrors();
            for(FieldError fieldError: fieldErrorList ){
                JsonObject error = new JsonObject();
                error.addProperty("field",fieldError.getField());
                error.addProperty("message",fieldError.getDefaultMessage());
                errors.add(error);
            }
            return result.toString();
        }


        Authentication token = new UsernamePasswordAuthenticationToken(
                loginForm.getUsername(), loginForm.getPassword());


        try {
            Authentication authentication = authenticationProvider.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            result.addProperty("result", "failure");
            result.addProperty("message","Login success");
            result.addProperty("url",servletContextPath + "/momo-pos/");

        }catch (UsernameNotFoundException e){
            result.addProperty("result", "failure");
            result.addProperty("message",e.getMessage());
        }catch (BadCredentialsException e){
            result.addProperty("result", "failure");
            result.addProperty("message",e.getMessage());
        }

        return result.toString();
    }
}
