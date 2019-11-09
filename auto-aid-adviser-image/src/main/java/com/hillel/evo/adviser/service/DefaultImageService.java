package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.entity.Image;
import com.hillel.evo.adviser.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultImageService implements ImageService{
    private final transient ImageRepository imageRepository;


    @Override
    public Image create(Image image) {
        return null;
    }

    @Override
    public List<Image> findAllByBusiness(Long id) {
        return null;
    }

    @Override
    public Image findById(Long id) {
        return null;
    }

    @Override
    public Image update(Image image) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
