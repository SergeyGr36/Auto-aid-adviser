package com.hillel.evo.adviser.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.transfer.MultipleFileUpload;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.hillel.evo.adviser.configuration.ImageConfigurationProperties;
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
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3CloudImageService implements CloudImageService {

    private final AmazonS3 amazonS3Client;
    private final TransferManager transferManager;
    private final ImageConfigurationProperties properties;

    @Override
    public boolean uploadFile(String keyFileName, MultipartFile file) {
        try {
            if(ImageValidation(file)) {
                InputStream inputStream = new ByteArrayInputStream(file.getBytes());
                ObjectMetadata meta = new ObjectMetadata();
                meta.setContentLength(file.getBytes().length);
                meta.setContentType(file.getContentType());
                amazonS3Client.putObject(new PutObjectRequest(properties.getBucketName(), keyFileName, inputStream, meta));
                return true;
            }
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }
        return false;
    }

    @Override
    public boolean uploadFileList(Long businessUserId, Long businessId, List<String> keyFileNames, List<MultipartFile> files) {
        Path tmpDirPath = Paths.get(System.getProperty("java.io.tmpdir")
                + "\\tmp-images" + "\\" + businessUserId.toString());
        File tmpDir = new File(tmpDirPath.toString());
        tmpDir.mkdirs();
        try {
            files.forEach(this::ImageValidation);
            for (int f = 0, k = 0; f < files.size(); f++, k++) {
                ImageToTmpDir(files.get(f), keyFileNames.get(k), tmpDirPath, businessId);
            }
            MultipleFileUpload upload = transferManager.uploadDirectory(
                    properties.getBucketName(),
                    businessUserId.toString(),
                    tmpDir,
                    true);
            upload.waitForCompletion();
            return true;
        }
        catch (IOException | SdkClientException | S3ServiceValidationException | InterruptedException ex){
            log.error(ex.getMessage(), ex);
            return false;
        }finally {
            transferManager.shutdownNow();
            try {
                FileUtils.forceDelete(tmpDir);
            } catch (IOException ex) {
                log.error(ex.getMessage(), ex);
            }
        }
    }

    private void ImageToTmpDir(MultipartFile file, String keyFileName, Path tmpDirPath, Long businessId) throws IOException {
        Path tmpBusinessPath = Paths.get(tmpDirPath + "\\" + businessId.toString());
        new File(tmpBusinessPath.toString()).mkdirs();
        Path tmpImagePath = Paths.get(
                tmpBusinessPath + "\\" + keyFileName.substring(keyFileName.lastIndexOf("/") + 1));
        file.transferTo(tmpImagePath);
    }

    private boolean ImageValidation (MultipartFile file)throws S3ServiceValidationException {
        if (file.isEmpty()){
            throw new S3ServiceValidationException("Image must be not empty: " + file.getOriginalFilename());
        }
        if(file.getSize() > properties.getImageMaxSize()){
            throw new S3ServiceValidationException("Image is to big: " + file.getOriginalFilename());
        }
        if (!file.getContentType().contains(MediaType.IMAGE_JPEG_VALUE)){
            throw new S3ServiceValidationException("Only JPEG image is accepted: " + file.getOriginalFilename());
        }
        return true;
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
