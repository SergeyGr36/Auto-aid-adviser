package com.hillel.evo.adviser.service;

import org.springframework.web.multipart.MultipartFile;

public interface CloudImageService {
    void uploadFile(String keyName, String filePath);
    void uploadFile(String keyName, MultipartFile file);
}
