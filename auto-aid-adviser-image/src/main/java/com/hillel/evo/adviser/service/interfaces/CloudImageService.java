package com.hillel.evo.adviser.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.Optional;

public interface CloudImageService {
    boolean uploadFile(String keyName, MultipartFile file);

    boolean deleteFile(String keyFileName);

    Optional<URL> generatePresignedURL(String keyFileName);
}
