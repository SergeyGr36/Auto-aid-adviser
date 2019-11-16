package com.hillel.evo.adviser.service.interfaces;

import com.hillel.evo.adviser.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.Optional;

public interface ImageService {
    Optional<Image> create(Long businessUserId, Long businessId, MultipartFile file);
    boolean delete(Image image);
    Optional<URL> generatePresignedURL(Image image);
    String generateKeyFileName(Long businessUserId, Long businessId, MultipartFile file);
}
