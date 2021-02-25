package com.farzan.springboot.webapp.cloudstorage.services;


import com.farzan.springboot.webapp.cloudstorage.mapper.CredentialMapper;
import com.farzan.springboot.webapp.cloudstorage.model.Credential;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
@Log4j2
public class CredentialService {

    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public int addCredential(Credential credential){

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credential.setPassword(encryptedPassword);
        credential.setKey(encodedKey);

        Credential newCredential = new Credential(null, credential.getUrl(), credential.getUsername(),
                credential.getKey(), credential.getPassword(), credential.getUserId());
        return credentialMapper.addCredential(newCredential);
    }

    public int updateCredential(Credential credential){

//        SecureRandom random = new SecureRandom();
//        byte[] key = new byte[16];
//        random.nextBytes(key);
//        String encodedKey = Base64.getEncoder().encodeToString(key);
        Credential cred = credentialMapper.getCredential(credential.getCredentialId());
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), cred.getKey());
        log.info("cred id: {}, dec pass: {}, enc key: {}", credential.getCredentialId(), credential.getPassword(),
                credential.getKey());

        return credentialMapper.updateCredential(new Credential(credential.getCredentialId(), credential.getUrl(),
                credential.getUsername(), credential.getKey(), encryptedPassword,credential.getUserId()));
    }

    public int deleteCredential(Integer credentialId, Integer userId){
        return credentialMapper.deleteCredential(credentialId, userId);
    }

    public List<Credential> getAllCredentialsByUserId(Integer userId){
        return credentialMapper.getAllCredentialsByUserId(userId);
    }
    public Credential getCrendentialById(Integer credentialId)
    {
        Credential credential = credentialMapper.getCredential(credentialId);
        credential.setPassword(encryptionService.decryptValue(credential.getPassword(),credential.getKey()));
        return credential;

    }

}
