package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.ImageDto;
import com.hillel.evo.adviser.entity.Image;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    Image toEntity(ImageDto dto);
    ImageDto toDto(Image image);
    List<ImageDto> toListDto(List<Image> images);
}
