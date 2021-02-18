package com.farzan.springboot.webapp.cloudstorage.mapper;

import com.farzan.springboot.webapp.cloudstorage.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO USERS (username, salt, password, firstname, lastname) VALUES (#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    Integer addUser(User user);


    @Select("SELECT * FROM USERS WHERE username = #{username}")
    User findUser(String username);
}
