package com.hillel.evo.adviser.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.transfer.MultipleFileUpload;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.hillel.evo.adviser.configuration.ImageConfigurationProperties;
import com.hillel.evo.adviser.dto.S3FileDTO;
import com.hillel.evo.adviser.exception.S3ServiceValidationException;
import com.hillel.evo.adviser.service.interfaces.CloudImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class S3CloudImageService implements CloudImageService {

    private final AmazonS3 amazonS3Client;
    private final TransferManager transferManager;
    private final ImageConfigurationProperties properties;

    @Override
    public boolean hasUploadedFile(String keyFileName, MultipartFile file) {
        try {
            isValid(file);
            InputStream inputStream = new ByteArrayInputStream(file.getBytes());
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(file.getBytes().length);
            meta.setContentType(file.getContentType());
            amazonS3Client.putObject(new PutObjectRequest(properties.getBucketName(), keyFileName, inputStream, meta));
            return true;
        } catch (IOException | S3ServiceValidationException ex) {
            log.error(ex.getMessage(), ex);
        }
        return false;
    }

    @Override
    public boolean hasUploadedFileList(String virtualDirectoryKeyPrefix, List<S3FileDTO> s3FileDTOs) throws S3ServiceValidationException{
        Path tmpDirPath = Path.of(
                System.getProperty("java.io.tmpdir") + File.separator + "tmp_imgs_" + UUID.randomUUID());
        try {
            s3FileDTOs.stream().map(S3FileDTO::getFile).forEach(this::isValid);
            Files.createDirectories(tmpDirPath);
            for (S3FileDTO dto : s3FileDTOs) {
                imageToTmpDir(dto.getFile(), dto.getUniqFileName(), tmpDirPath);
            }
            MultipleFileUpload upload = transferManager.uploadDirectory(
                    properties.getBucketName(),
                    virtualDirectoryKeyPrefix,
                    tmpDirPath.toFile(),
                    false);
            upload.waitForCompletion();
            return true;
        } catch (IOException | SdkClientException | InterruptedException ex){
            log.error(ex.getMessage(), ex);
            return false;
        } catch (S3ServiceValidationException ex){
            log.error(ex.getMessage());
            throw new S3ServiceValidationException(ex.getMessage());
        } finally {
            try {
                FileUtils.forceDelete(tmpDirPath.toFile());
            } catch (IOException ex) {
                log.error(ex.getMessage(), ex);
            }
        }
    }

    private void imageToTmpDir(MultipartFile file, String uniqFileName, Path tmpDirPath) throws IOException {
        Path tmpImagePath = Paths.get(tmpDirPath + File.separator + uniqFileName);
        try {
        file.transferTo(tmpImagePath);
        } catch (IOException ex){
            log.error(ex.getMessage(), ex);
            throw new FileNotFoundException(String.format("Failed to convert %s to tmp file", file.getOriginalFilename()));
        }
    }

    private void isValid (MultipartFile file) throws S3ServiceValidationException {
        if (file.isEmpty()) {
            throw new S3ServiceValidationException(String.format("image %s must be not empty", file.getOriginalFilename()));
        }
        if (file.getSize() > properties.getImageMaxSize()) {
            throw new S3ServiceValidationException(String.format("image %s is to big", file.getOriginalFilename()));
        }
        if (!file.getContentType().contains(MediaType.IMAGE_JPEG_VALUE) &&
            !file.getContentType().contains(MediaType.IMAGE_PNG_VALUE)) {
            throw new S3ServiceValidationException("only JPEG or PNG image is accepted, but received: " + file.getContentType());
        }
    }

    @Override
    public boolean hasDeletedFile(String keyFileName) {
        try {
            amazonS3Client.deleteObject(new DeleteObjectRequest(properties.getBucketName(), keyFileName));
            return true;
        } catch (SdkClientException ex) {
            log.error(ex.getMessage(), ex);
            return false;
        }
    }

    @Override
    public boolean hasDeletedFileList(List<KeyVersion> keyFileNames) {
        try {
            amazonS3Client.deleteObjects(new DeleteObjectsRequest(
                    properties.getBucketName()).withKeys(keyFileNames).withQuiet(false));
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
