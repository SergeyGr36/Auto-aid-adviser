package com.hillel.evo.adviser.service.interfaces;

import com.hillel.evo.adviser.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.List;
import java.util.Optional;

public interface ImageService {
    Optional<Image> create(Long businessUserId, Long businessId, MultipartFile file);

    Optional<List<Image>> create(Long businessUserId, Long businessId, List<MultipartFile> files);

    boolean delete(Image image);

    boolean delete(List<Image> images);

    Optional<URL> generatePresignedURL(Image image);

}
