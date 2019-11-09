package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final transient ImageRepository imageRepository;
}
