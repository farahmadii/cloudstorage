package com.farzan.springboot.webapp.cloudstorage.services;


import com.farzan.springboot.webapp.cloudstorage.mapper.UserMapper;
import com.farzan.springboot.webapp.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {
    private UserMapper userMapper;
    private HashService hashService;

    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public boolean isUsernameAvailable(String username){ return userMapper.findUser(username) == null;}

    public int createUser(User user){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        return userMapper.addUser(new User(null, user.getUsername(), encodedSalt, hashedPassword,
                user.getFirstName(), user.getLastName()));
    }

    public User findUser(String username){
        return userMapper.findUser(username);
    }

}
