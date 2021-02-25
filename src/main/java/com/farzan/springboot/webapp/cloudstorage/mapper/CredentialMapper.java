package com.farzan.springboot.webapp.cloudstorage.mapper;


import com.farzan.springboot.webapp.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    Credential getCredential(int credentialId);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    List<Credential> getAllCredentialsByUserId(int userId);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, userid, password) VALUES (#{url}, #{username}, #{key}, #{userId}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    Integer addCredential(Credential credential);

    @Update("UPDATE CREDENTIALS SET username=#{username}, password=#{password} WHERE credentialid=#{credentialId} and userid = #{userId}")
    Integer updateCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid =#{credentialId} and userid =#{userId}")
    Integer deleteCredential(int credentialId, int userId);
}