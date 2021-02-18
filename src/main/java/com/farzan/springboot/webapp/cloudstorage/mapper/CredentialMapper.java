package com.farzan.springboot.webapp.cloudstorage.mapper;


import com.farzan.springboot.webapp.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId} and credentialid = #{credentialId}")
    Credential getCredential(int userId, int credentialId);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, userid, password) VALUES (#{url}, #{username}, #{key} #{userId}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    Integer addCredential(Credential credential);

    @Update("UPDATE CREDENTIALS SET username=#{username}, password=#{password} WHERE credentialid=#{credentialId} and userid = #{userId}")
    void updateCredential(Credential credential, int userId);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid =#{credentialId} and userid =#{userId}")
    void deleteCredential(int credentialId, int userId);
}