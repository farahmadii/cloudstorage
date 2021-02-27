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
using Spring MVC's application model to identify the templates (here: /signup.html) served for different requests and
populating the view model with data needed by the template.
 */

@Controller
@RequestMapping("/signup")
public class SignupController {
    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String signupView(){
        return "signup";
    }

    @PostMapping
    public String signupUser(@ModelAttribute User user, Model model){
        String signupError = null;

        // username is already taken
        if(!userService.isUsernameAvailable(user.getUsername())){
            signupError = "username is already taken.";
        }

        if(signupError == null){
            int rowsAdded = userService.createUser(user);
            if(rowsAdded < 0){
                signupError = "There was an error signing you up, please try a bit later.";
            }
        }

        if(signupError == null){
            model.addAttribute("signupSuccess", true);
            return "login";
        }else{
            model.addAttribute("signupError", signupError);
        }
        return "signup";
    }
}
