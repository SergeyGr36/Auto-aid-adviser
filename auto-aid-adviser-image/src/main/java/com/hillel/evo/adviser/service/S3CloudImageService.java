package com.hillel.evo.adviser.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.hillel.evo.adviser.configuration.ImageConfigurationProperties;
import com.hillel.evo.adviser.service.interfaces.CloudImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3CloudImageService implements CloudImageService {
    private final AmazonS3 amazonS3Client;
    private final ImageConfigurationProperties properties;

    @Override
    public boolean uploadFile(String keyFileName, MultipartFile file) {
        try {
            InputStream inputStream = new ByteArrayInputStream(file.getBytes());
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(file.getBytes().length);
            meta.setContentType(file.getContentType());
            amazonS3Client.putObject(new PutObjectRequest(properties.getBucketName(), keyFileName, inputStream, meta));
            return true;
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
            return false;
        }
    }

    @Override
    public boolean deleteFile(String keyFileName) {
        try {
            amazonS3Client.deleteObject(new DeleteObjectRequest(properties.getBucketName(), keyFileName));
            return true;
        } catch (SdkClientException ex) {
            log.error(ex.getMessage(), ex);
            return false;
        }
    }

    @Override
    public Optional<URL> generatePresignedURL(String keyFileName) {
        try {
            java.util.Date expiration = new java.util.Date();
            expiration.setTime(expiration.getTime() + properties.getExpiresIn());
            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(properties.getBucketName(), keyFileName)
                            .withMethod(HttpMethod.GET)
                            .withExpiration(expiration);
            URL url = amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest);
            return Optional.of(url);
        } catch (SdkClientException ex) {
            log.error(ex.getMessage(), ex);
            return Optional.empty();
        }
    }
}
