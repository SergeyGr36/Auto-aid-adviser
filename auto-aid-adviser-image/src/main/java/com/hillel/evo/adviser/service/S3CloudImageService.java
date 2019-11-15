package com.hillel.evo.adviser.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.hillel.evo.adviser.configuration.ImageConfigurationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
public class S3CloudImageService implements CloudImageService {
    private final TransferManager transferManager;
    private final ImageConfigurationProperties properties;

    @Override
    public void uploadFile(String keyName, String filePath) {
        Upload upload = transferManager.upload(properties.getBucket(), keyName, new File(filePath));
        // Optionally, wait for the upload to finish before continuing?
        //upload.waitForCompletion();
    }

    @Override
    public void uploadFile(String keyName, MultipartFile file) {
        ObjectMetadata metadata = new ObjectMetadata();
        try {
            transferManager.upload(properties.getBucket(), keyName, file.getInputStream(), metadata);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
