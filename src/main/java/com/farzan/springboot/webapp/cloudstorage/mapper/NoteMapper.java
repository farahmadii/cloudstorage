package com.farzan.springboot.webapp.cloudstorage.mapper;

import com.farzan.springboot.webapp.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;


@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES WHERE userid = #{userId} and noteid = #{noteId}")
    Note getNote(int userId, int noteId);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    Integer addNote(Note note);

    @Update("UPDATE NOTES SET notetitle=#{noteTitle}, notedescription=#{noteDescription} WHERE noteid =#{noteId} and userid=#{userId}")
    void updateNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid =#{noteId} and userid =#{userId}")
    void deleteNote(int noteId, int userId);
}
