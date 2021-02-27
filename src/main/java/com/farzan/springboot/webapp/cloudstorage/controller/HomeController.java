package com.farzan.springboot.webapp.cloudstorage.controller;



import com.farzan.springboot.webapp.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(value = "/home")
public class HomeController {
    private UserService userService;
    private EncryptionService encryptionService;
    private NoteService noteService;
    private FileStorageService fileStorageService;
    private CredentialService credentialService;

    public HomeController(UserService userService, EncryptionService encryptionService, NoteService noteService,
                          FileStorageService fileStorageService, CredentialService credentialService) {
        this.userService = userService;
        this.encryptionService = encryptionService;
        this.noteService = noteService;
        this.fileStorageService = fileStorageService;
        this.credentialService = credentialService;
    }


    @GetMapping
    public String getHomePage(Authentication authentication, Model model) {
        Integer userId = this.userService.findUser(authentication.getName()).getUserId();
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("credentials", this.credentialService.getAllCredentialsByUserId(userId));
        model.addAttribute("notes", this.noteService.getAllNotesByUserId(userId));
        model.addAttribute("files", this.fileStorageService.getAllFilesByUserId(userId));

        return "home";
    }

}
