package com.farzan.springboot.webapp.cloudstorage.mapper;

import com.farzan.springboot.webapp.cloudstorage.model.File;
import com.farzan.springboot.webapp.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES WHERE userid = #{userId} and noteid = #{noteId}")
    Note getNote(Integer userId, Integer noteId);

    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<Note> getAllNotes(Integer userId);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    Integer addNote(Note note);

    @Update("UPDATE NOTES SET notetitle=#{noteTitle}, notedescription=#{noteDescription} WHERE noteid =#{noteId} and userid=#{userId}")
    Integer updateNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid =#{noteId} and userid =#{userId}")
    Integer deleteNote(Integer noteId, Integer userId);

    @Select("SELECT * FROM NOTES WHERE userid =#{userId} and notetitle =#{noteTitle} and notedescription =#{noteDescription}")
    File noteTitleAndDescriptionExist(Integer userId, String noteTitle, String noteDescription);
}
