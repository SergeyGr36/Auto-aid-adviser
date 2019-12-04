package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.ImageDto;
import com.hillel.evo.adviser.entity.Image;
import com.hillel.evo.adviser.service.interfaces.CloudImageService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ImageMapper {

    @Autowired
    CloudImageService cloudImageService;

    @Mapping(target = "urlImage", expression = "java(cloudImageService.generatePresignedURL(image.getKeyFileName()).get())")
    public abstract ImageDto toDto(Image image);

    public abstract List<ImageDto> toListDto(List<Image> images);

}
