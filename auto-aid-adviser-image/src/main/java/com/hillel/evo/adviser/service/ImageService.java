package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.entity.Image;

import java.util.List;
import java.util.Optional;

public interface ImageService {
    Image create(Image image);
    List<Image> findAllByBusiness(Long id);
    Optional<Image> findById(Long id);
    Image update(Image image);
    void delete(Image image);
}
