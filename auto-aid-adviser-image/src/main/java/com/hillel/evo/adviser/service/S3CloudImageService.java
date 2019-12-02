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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class S3CloudImageService implements CloudImageService {

    private final AmazonS3 amazonS3Client;
    private final TransferManager transferManager;
    private final ImageConfigurationProperties properties;

    @Override
    public boolean uploadFile(String keyFileName, MultipartFile file) {
        try {
            imageValidation(file);
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
    public boolean uploadFileList(String virtualDirectoryKeyPrefix, List<S3FileDTO> s3FileDTOs) {
        try {
            s3FileDTOs.stream().map(S3FileDTO::getFile).forEach(this::imageValidation);
            Path tmpDirPath = Files.createTempDirectory("tmp-images");
            for (S3FileDTO dto : s3FileDTOs) {
                imageToTmpDir(dto.getFile(), dto.getUniqFileName(), tmpDirPath);
            }
            MultipleFileUpload upload = transferManager.uploadDirectory(
                    properties.getBucketName(),
                    virtualDirectoryKeyPrefix,
                    tmpDirPath.toFile(),
                    false);
            upload.waitForCompletion();
            FileUtils.forceDelete(tmpDirPath.toFile());
            return true;
        } catch (IOException | SdkClientException | S3ServiceValidationException | InterruptedException ex){
            log.error(ex.getMessage(), ex);
            return false;
        } finally {
            transferManager.shutdownNow();
        }
    }

    private void imageToTmpDir(MultipartFile file, String uniqFileName, Path tmpDirPath) throws IOException {
        Path tmpImagePath = Paths.get(tmpDirPath + "\\" + uniqFileName);
        try {
        file.transferTo(tmpImagePath);
        } catch (IOException ex){
            log.error(ex.getMessage(), ex);
            throw new FileNotFoundException(String.format("Failed to convert %s to tmp file", file.getOriginalFilename()));
        }
    }

    private void imageValidation (MultipartFile file)throws S3ServiceValidationException {
        if (file.isEmpty()){
            throw new S3ServiceValidationException("Image must be not empty: " + file.getOriginalFilename());
        }
        if(file.getSize() > properties.getImageMaxSize()){
            throw new S3ServiceValidationException("Image is to big: " + file.getOriginalFilename());
        }
        if (!file.getContentType().contains(MediaType.IMAGE_JPEG_VALUE)){
            throw new S3ServiceValidationException("Only JPEG image is accepted: " + file.getOriginalFilename());
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
    public boolean deleteFileList(List<KeyVersion> keyFileNames) {
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
