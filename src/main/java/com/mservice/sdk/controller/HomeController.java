package com.mservice.sdk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by jinz on 4/15/17.
 */
@Controller
public class HomeController {

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String getHomePage(Model model){
        model.addAttribute("home","Hai");
        return "index";
    }
}
