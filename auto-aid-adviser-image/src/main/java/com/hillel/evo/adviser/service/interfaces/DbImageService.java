package com.hillel.evo.adviser.service.interfaces;

import com.hillel.evo.adviser.entity.Image;

import java.util.Optional;

public interface DbImageService {
    Image create(Image image);
    Optional<Image> findById(Long id);
    Image update(Image image);
    void delete(Image image);
}
