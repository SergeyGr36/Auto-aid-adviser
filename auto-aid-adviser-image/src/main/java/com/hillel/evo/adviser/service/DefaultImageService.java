package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.entity.Image;
import com.hillel.evo.adviser.repository.ImageRepository;
import com.hillel.evo.adviser.service.interfaces.CloudImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultImageService implements com.hillel.evo.adviser.service.interfaces.ImageService {
    private final CloudImageService cloudService;
    private final ImageRepository repository;

    @Override
    public Optional<Image> create(Long businessUserId, Long businessId, MultipartFile file) {
        String keyFileName = generateKeyFileName(businessUserId, businessId, file);
        if (cloudService.uploadFile(keyFileName, file)) {
            String originalFileName = file.getOriginalFilename();
            Image image = new Image(keyFileName, originalFileName);
            return Optional.of(repository.save(image));
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<Image>> create(Long businessUserId, Long businessId, List<MultipartFile> files) {
        List<String> keyFileNames = new ArrayList<>();
        files.forEach(f->keyFileNames.add(generateKeyFileName(businessUserId,businessId,f)));
        if(cloudService.uploadFileList(businessUserId, businessId, keyFileNames, files)){
            List<Image> images = new ArrayList<>();
            for (int k = 0, f = 0; f < files.size(); k++, f++){
                images.add(new Image(keyFileNames.get(k), files.get(f).getOriginalFilename()));
            }
            return Optional.of(repository.saveAll(images));
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Image image) {
        if (cloudService.deleteFile(image.getKeyFileName())) {
            repository.delete(image);
            return true;
        }
        return false;
    }

    @Override
    public Optional<URL> generatePresignedURL(Image image) {
        return cloudService.generatePresignedURL(image.getKeyFileName());
    }

    @Override
    public String generateKeyFileName(Long businessUserId, Long businessId, MultipartFile file) {
        return businessUserId.toString() + "/"
                + businessId.toString() + "/"
                + UUID.randomUUID().toString()
                + "-" + file.getOriginalFilename();
    }
}
