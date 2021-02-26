package com.farzan.springboot.webapp.cloudstorage.controller;


import com.farzan.springboot.webapp.cloudstorage.model.Credential;
import com.farzan.springboot.webapp.cloudstorage.model.User;
import com.farzan.springboot.webapp.cloudstorage.services.CredentialService;
import com.farzan.springboot.webapp.cloudstorage.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CredentialsController {

    private UserService userService;
    private CredentialService credentialService;

    public CredentialsController(UserService userService, CredentialService credentialService) {
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @PostMapping("/credentials")
    public ModelAndView addCredential(Authentication authentication, @ModelAttribute Credential credential, Model model){

        User user = userService.findUser(authentication.getName());
        Integer userId = user.getUserId();
        credential.setUserId(userId);

        try{
            if(credential.getCredentialId() == null){
                credentialService.addCredential(credential);
                model.addAttribute("success", true);
                model.addAttribute("message", "- Credentials added!");
            } else {
                credentialService.updateCredential(credential);
                model.addAttribute("success", true);
                model.addAttribute("message", "- Credentials updated!");
            }
        }
        catch(Exception e){

            model.addAttribute("error", true);
            model.addAttribute("message", "- System error!" + e.getMessage());
        }

        return new ModelAndView("result");
    }


    @GetMapping("/home/delete-credential/{credentialId}")
    public ModelAndView deleteCredential(@PathVariable("credentialId") Integer credentialId, Authentication authentication, Model model){

        User user = userService.findUser(authentication.getName());
        Integer userId = user.getUserId();
        try{
            credentialService.deleteCredential(credentialId, userId);
            model.addAttribute("success", true);
            model.addAttribute("message", "- Credentials deleted!");
        }
        catch(Exception e){
            model.addAttribute("error", true);
            model.addAttribute("message", "- System error!" + e.getMessage());
        }

        return new ModelAndView("result");
    }

    @GetMapping(value = "/Edit/{credentialId}",produces={MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Credential getCredentialById(@PathVariable("credentialId")Integer credentialId)
    {
        Credential credential = credentialService.getCrendentialById(credentialId);
        return credential;
    }
}
