package com.farzan.springboot.webapp.cloudstorage.mapper;

import com.farzan.springboot.webapp.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;


@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE userid = #{userId} and fileid = #{fileId}")
    File getFile(int userId, int fileId);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES (#{fileName}, #{contentType}, #{fileSize} #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer addFile(File file);

//    @Update("UPDATE FILES SET  WHERE ")
//    void updateFile(File file);

    @Delete("DELETE FROM FILES WHERE fileid =#{fileId} and userid =#{userId}")
    void deleteFile(int fileId, int userId);
}
