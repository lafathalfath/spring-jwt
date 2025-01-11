package com.mysql.spring_jwt.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.spring_jwt.component.PathComponent;

@Service
public class FileStorageService {

    @Value("${server.base-url}")
    private String BASE_URL;

    public String store(MultipartFile file, String subject) throws IOException {
        String targetPath = PathComponent.STORAGE_PATH + subject + "/";
        File targetDir = new File(targetPath);
        if (!targetDir.exists()) {
            boolean createdDir = targetDir.mkdirs();
            if (!createdDir) return "Failed to create directories";
        }

        String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        String fileUrl = BASE_URL + "/api/storage/" + subject + "/" + filename;

        String filePath = targetPath + filename;
        file.transferTo(new File(filePath));
        
        return fileUrl;
    }

}
