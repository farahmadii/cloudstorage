package com.farzan.springboot.webapp.cloudstorage.services;


import com.farzan.springboot.webapp.cloudstorage.mapper.FileMapper;
import com.farzan.springboot.webapp.cloudstorage.model.File;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@Log4j2
public class FileStorageService {
    private FileMapper fileMapper;

    public FileStorageService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public int uploadFile(Integer userId, MultipartFile fileUpload) throws IOException, FileNotFoundException {
        File newFile = new File();
        newFile.setUserId(userId);
        newFile.setFileData(fileUpload.getBytes());
        newFile.setFileName(fileUpload.getOriginalFilename());
        newFile.setFileSize(fileUpload.getSize());
        newFile.setContentType(fileUpload.getContentType());
        return fileMapper.addFile(newFile);

    }

    public int deleteFile(Integer fileId, Integer userId) {
        return fileMapper.delete(fileId, userId);
    }

    public List<File> getAllFilesByUserId(Integer userId){ return fileMapper.getAllFilesByUserId(userId);}

    public boolean fileNameExists(String fileName, Integer userId){
        return (this.fileMapper.fileNameExists(fileName, userId) != null);
    }
    public File getFileById(Integer fileId) {
        return this.fileMapper.getFileById(fileId);
    }
}
