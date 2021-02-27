package com.farzan.springboot.webapp.cloudstorage.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
using Spring MVC's application model to identify the templates (here: /login.html) served for different requests and
populating the view model with data needed by the template.
 */

@Controller
@Log4j2
public class LoginController {


    @GetMapping("/login")
    public String loginView(@RequestParam(required = false) String logout, Model model){
        if(logout != null){
            model.addAttribute("logoutSuccess", true);
        }
        return "login";
    }

}
