package com.hillel.evo.adviser.service.interfaces;

import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import com.hillel.evo.adviser.dto.S3FileDTO;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.List;
import java.util.Optional;

public interface CloudImageService {
    boolean hasUploadedFile(String keyName, MultipartFile file);

    boolean hasUploadedFileList(String virtualDirectoryKeyPrefix, List<S3FileDTO> s3FileDTOs);

    boolean hasDeletedFile(String keyFileName);

    boolean hasDeletedFileList(List<KeyVersion> keyFileNames);

    Optional<URL> generatePresignedURL(String keyFileName);
}
