package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.entity.Image;
import com.hillel.evo.adviser.repository.ImageRepository;
import com.hillel.evo.adviser.service.interfaces.DbImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultDbImageService implements DbImageService {
    private final transient ImageRepository imageRepository;
    @Override
    public Image create(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public Optional<Image> findById(Long id) {
        return imageRepository.findById(id);
    }

    @Override
    public Image update(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public void delete(Image image) {
        imageRepository.delete(image);
    }
}
