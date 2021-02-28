package com.farzan.springboot.webapp.cloudstorage.controller;

import com.farzan.springboot.webapp.cloudstorage.model.File;
import com.farzan.springboot.webapp.cloudstorage.model.User;
import com.farzan.springboot.webapp.cloudstorage.services.FileStorageService;
import com.farzan.springboot.webapp.cloudstorage.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;




@Controller
@Log4j2
public class FileController {

    private FileStorageService fileStorageService;
    private UserService userService;

    public FileController(FileStorageService fileStorageService, UserService userService) {
        this.fileStorageService = fileStorageService;
        this.userService = userService;
    }

    @PostMapping(value="/file-upload")
    public ModelAndView handleFileUpload(Authentication authentication, @RequestParam("fileUpload") MultipartFile fileUpload, Model model){
        // check if no file is selected to upload
        if (fileUpload.isEmpty()) {
            model.addAttribute("success", false);
            model.addAttribute("error", true);
            model.addAttribute("message", "- No file selected to upload");
            return new ModelAndView("result");
        }
        User user = userService.findUser(authentication.getName());
        Integer userId = user.getUserId();


        // check if the file name already exists
        if (fileStorageService.fileNameExists(fileUpload.getOriginalFilename(), userId)) {
            model.addAttribute("success", false);
            model.addAttribute("error", true);
            model.addAttribute("message", "- File name already exists");
            return new ModelAndView("result");
        }
        // applying limits on file size
        if (fileUpload.getSize() > 104857600){
            model.addAttribute("success", false);
            model.addAttribute("error", true);
            model.addAttribute("message", "- File size exceeds the limits, file size should be < 100MB");
            return new ModelAndView("result");
        }
        try {
            fileStorageService.uploadFile(userId, fileUpload);
            model.addAttribute("success", true);
            model.addAttribute("message", "- File uploaded successfully");
        } catch (Exception e) {
                model.addAttribute("error", true);
                model.addAttribute("message", "- System error!" + e.getMessage());
        }
        return new ModelAndView("result");
    }

    @GetMapping("/home/file/delete/{fileId}")
    public ModelAndView deleteFile(@PathVariable("fileId")Integer fileId, Authentication authentication, Model model) {
        User user = this.userService.findUser(authentication.getName());
        Integer userId = user.getUserId();
        log.info("userid: {}", userId);


        try {
            log.info("should be here!");
            this.fileStorageService.deleteFile(fileId, userId);
            model.addAttribute("success", true);
            model.addAttribute("message", "- File deleted");
        } catch (Exception e) {
            model.addAttribute("error", true);
            model.addAttribute("message", "- System error!" + e.getMessage());
        }
        return new ModelAndView("result");
    }


//    @GetMapping("/download/{fileId}")
    @GetMapping("/home/file/download/{fileId}")
    public ResponseEntity<Resource> download(@PathVariable("fileId") Integer fileId) {
        File file = fileStorageService.getFileById(fileId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(httpHeaders.CONTENT_DISPOSITION, "attachment; filename = " + file.getFileName());
        httpHeaders.add("Cache-control", "no-cache, no-store, must-revalidate");
        httpHeaders.add("Pragma", "no-cache");
        httpHeaders.add("Expires", "0");
        ByteArrayResource resource = new ByteArrayResource(file.getFileData());
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(resource);

    }
}
