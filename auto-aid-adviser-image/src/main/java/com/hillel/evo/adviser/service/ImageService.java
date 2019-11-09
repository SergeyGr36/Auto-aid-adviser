package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.entity.Image;

import java.util.List;

public interface ImageService {
    Image create(Image image);
    List<Image> findAllByBusiness(Long id);
    Image findById(Long id);
    Image update(Image image);
    void delete(Long id);
}
