package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.ImageDto;
import com.hillel.evo.adviser.entity.Image;
import com.hillel.evo.adviser.service.interfaces.CloudImageService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapping;

import java.util.List;

@RequiredArgsConstructor
public abstract class ImageMapper {

    protected final CloudImageService cloudImageService;

    public abstract ImageDto toDto(Image image);

    public abstract List<ImageDto> toListDto(List<Image> images);

}
