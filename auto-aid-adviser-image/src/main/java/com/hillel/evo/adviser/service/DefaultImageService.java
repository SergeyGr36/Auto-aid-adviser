package com.hillel.evo.adviser.service;

import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import com.hillel.evo.adviser.dto.S3FileDTO;
import com.hillel.evo.adviser.entity.Image;
import com.hillel.evo.adviser.exception.S3ServiceValidationException;
import com.hillel.evo.adviser.repository.ImageRepository;
import com.hillel.evo.adviser.service.interfaces.CloudImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class DefaultImageService implements com.hillel.evo.adviser.service.interfaces.ImageService {
    private final CloudImageService cloudService;
    private final ImageRepository repository;

    @Override
    public Optional<Image> create(Long businessUserId, Long businessId, MultipartFile file) {
        String keyFileName = generateKeyFileName(
                generateDirectoryKeyPrefix(businessUserId, businessId), generateUniqFileName(file));
        if (cloudService.hasUploadedFile(keyFileName, file)) {
            String originalFileName = file.getOriginalFilename();
            Image image = new Image(keyFileName, originalFileName);
            return Optional.of(repository.save(image));
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<Image>> create(Long businessUserId, Long businessId, List<MultipartFile> files) throws S3ServiceValidationException {
        String virtualDirectoryKeyPrefix = generateDirectoryKeyPrefix(businessUserId, businessId);
        List<S3FileDTO> s3FileDTOs = createListDTOs(files, virtualDirectoryKeyPrefix);
        if(cloudService.hasUploadedFileList(virtualDirectoryKeyPrefix, s3FileDTOs)){
            List<Image> images = s3FileDTOs.stream().map(
                    s3FileDTO -> new Image(s3FileDTO.getKeyFileName(), s3FileDTO.getFile().getOriginalFilename()))
                    .collect(Collectors.toList());
            return Optional.of(repository.saveAll(images));
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Image image) {
        if (cloudService.hasDeletedFile(image.getKeyFileName())) {
            repository.delete(image);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(List<Image> images) {
        List<KeyVersion> keyFileNames = images.stream()
                .map(image -> new KeyVersion(image.getKeyFileName())).collect(Collectors.toList());
        if (cloudService.hasDeletedFileList(keyFileNames)) {
            repository.deleteAll(images);
            return true;
        }
        return false;
    }

    @Override
    public Optional<URL> generatePresignedURL(Image image) {
        return cloudService.generatePresignedURL(image.getKeyFileName());
    }

    private String generateKeyFileName(String virtualDirectoryKeyPrefix, String uniqFileName) {
        return virtualDirectoryKeyPrefix + "/" + uniqFileName;
    }

    private String generateUniqFileName(MultipartFile file) {
        return UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
    }

    private String generateDirectoryKeyPrefix (Long businessUserId, Long businessId) {
        return businessUserId.toString() + "/" + businessId.toString();
    }

    private List<S3FileDTO> createListDTOs (List<MultipartFile> files, String virtualDirectoryKeyPrefix) {
        List<S3FileDTO> s3FileDTOs = new ArrayList<>();
        for (MultipartFile file : files) {
            String uniqFileName = generateUniqFileName(file);
            String keyFileName = generateKeyFileName(virtualDirectoryKeyPrefix, uniqFileName);
            s3FileDTOs.add(new S3FileDTO(file, uniqFileName, keyFileName));
        }
        return s3FileDTOs;
    }
}
