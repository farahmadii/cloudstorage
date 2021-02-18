package com.farzan.springboot.webapp.cloudstorage.model;

public class Notes {
    private Integer noteId;
    private String noteDescription;
    private Integer userId;

    public Notes(Integer noteId, String noteDescription, Integer userId) {
        this.noteId = noteId;
        this.noteDescription = noteDescription;
        this.userId = userId;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
