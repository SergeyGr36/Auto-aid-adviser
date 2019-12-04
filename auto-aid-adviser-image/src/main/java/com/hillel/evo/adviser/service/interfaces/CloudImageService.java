package com.hillel.evo.adviser.service.interfaces;

import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import com.hillel.evo.adviser.dto.S3FileDTO;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.List;
import java.util.Optional;

public interface CloudImageService {
    boolean hasUploaded(String keyName, MultipartFile file);

    boolean hasUploaded(String virtualDirectoryKeyPrefix, List<S3FileDTO> s3FileDTOs);

    boolean hasDeleted(String keyFileName);

    boolean hasDeleted(List<KeyVersion> keyFileNames);

    Optional<URL> generatePresignedURL(String keyFileName);
}
