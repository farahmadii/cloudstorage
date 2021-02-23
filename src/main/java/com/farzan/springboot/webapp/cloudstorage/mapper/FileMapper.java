package com.farzan.springboot.webapp.cloudstorage.mapper;

import com.farzan.springboot.webapp.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE fileid = #{fileId}")
    File getFileById(Integer fileId);

    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<File> getAllFilesByUserId(Integer userId);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata)" +
            "VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer addFile(File file);

//    @Update("UPDATE FILES SET  WHERE ")
//    void updateFile(File file);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileId} AND userid = #{userId}")
    Integer delete(Integer fileId, Integer userId);
    //Integer delete(File file);

    @Select("SELECT * FROM FILES WHERE filename =#{fileName} and userid =#{userId}")
    File fileNameExists(String fileName, Integer userId);
}
