package com.farzan.springboot.webapp.cloudstorage.services;

import com.farzan.springboot.webapp.cloudstorage.mapper.NoteMapper;
import com.farzan.springboot.webapp.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int addNote(Note note){
        Note newNote = new Note(null,note.getNoteTitle(),note.getNoteDescription(),note.getUserId());
        return noteMapper.addNote(note);
    }

    public int editNote(Note note){
        return noteMapper.updateNote(note);
    }

    public int deleteNote(Integer noteId, Integer userId){
        return noteMapper.deleteNote(noteId, userId);
    }

    public List<Note> getAllNotesByUserId(Integer userId){
        return noteMapper.getAllNotes(userId);
    }

}
