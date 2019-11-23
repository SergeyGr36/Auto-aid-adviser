package com.hillel.evo.adviser.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.List;
import java.util.Optional;

public interface CloudImageService {
    boolean uploadFile(String keyName, MultipartFile file);

    boolean uploadFileList(Long businessUserId, Long businessId, List<String> keyFileNames, List<MultipartFile> files);

    boolean deleteFile(String keyFileName);

    Optional<URL> generatePresignedURL(String keyFileName);
}
