package com.farzan.springboot.webapp.cloudstorage.controller;


import com.farzan.springboot.webapp.cloudstorage.model.User;
import com.farzan.springboot.webapp.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*
using Spring MVC's application model to identify the templates (here: /login.html) served for different requests and
populating the view model with data needed by the template.
 */

@Controller
@RequestMapping("/login")
public class LoginController {


    @GetMapping
    public String loginView(){
        return "login";
    }

}
