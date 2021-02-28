package com.farzan.springboot.webapp.cloudstorage.controller;


import com.farzan.springboot.webapp.cloudstorage.model.Note;
import com.farzan.springboot.webapp.cloudstorage.model.User;
import com.farzan.springboot.webapp.cloudstorage.services.NoteService;
import com.farzan.springboot.webapp.cloudstorage.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Log4j2
public class NoteController {

    private NoteService noteService;
    private UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping("/notes")
    public ModelAndView addNewNote(@ModelAttribute Note note, Authentication authentication, Model model){

        User user = userService.findUser(authentication.getName());
        Integer userId = user.getUserId();

        note.setUserId(userId);
        log.info("note id: {}, note title: {}, note desc: {}, user id: {}", note.getNoteId(), note.getNoteTitle(), note.getNoteDescription(), note.getUserId());

        try {
             // this is when the popup is opened to create a new note (noteId is null)
            if (note.getNoteId() == null) {
                // check if the same content and title is already in DB for notes
                if(noteService.NoteTitleAndDescriptionExist(userId, note.getNoteTitle(), note.getNoteDescription())) {
                    model.addAttribute("success", false);
                    model.addAttribute("error", true);
                    model.addAttribute("message", "- same note already exists");
                    return new ModelAndView("result");
                }

                log.info("*************  user id: {}", userId);
                noteService.addNote(note);
                model.addAttribute("success", true);
                model.addAttribute("message", "- Note added!");
                // this is when the popup is opened to update an existing note
            } else {
                noteService.editNote(note);
                model.addAttribute("success", true);
                model.addAttribute("message", "- Note updated!");
            }
        } catch (Exception e) {
            model.addAttribute("error", true);
            model.addAttribute("message", "- System error!" + e.getMessage());
        }
        return new ModelAndView("result");
    }

    @GetMapping("/home/delete-note/{noteId}")
    public ModelAndView deleteNote(@PathVariable("noteId") Integer noteId, Authentication authentication, Model model){
        User user = userService.findUser(authentication.getName());
        Integer userId = user.getUserId();
        try{
            noteService.deleteNote(noteId, userId);
            model.addAttribute("success", true);
            model.addAttribute("message", "- Note deleted!");
        }
        catch(Exception e){
            model.addAttribute("error", true);
            model.addAttribute("message", "- System error!" + e.getMessage());
        }
        return new ModelAndView("result");
    }
}
