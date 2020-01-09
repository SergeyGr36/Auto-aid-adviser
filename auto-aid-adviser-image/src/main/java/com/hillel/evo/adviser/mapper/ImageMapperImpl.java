package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.ImageDto;
import com.hillel.evo.adviser.entity.Image;
import com.hillel.evo.adviser.service.interfaces.CloudImageService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@SuppressWarnings("PMD")
public class ImageMapperImpl extends ImageMapper {

    public ImageMapperImpl(CloudImageService cloudImageService) {
        super(cloudImageService);
    }

    @Override
    public ImageDto toDto(Image image) {
        if ( image == null ) {
            return null;
        }

        ImageDto imageDto = new ImageDto();

        imageDto.setId( image.getId() );
        imageDto.setOriginalFileName( image.getOriginalFileName() );

        imageDto.setUrlImage( cloudImageService.generatePresignedURL(image.getKeyFileName()).get() );

        return imageDto;
    }

    @Override
    public List<ImageDto> toListDto(List<Image> images) {
        if ( images == null ) {
            return null;
        }

        List<ImageDto> list = new ArrayList<ImageDto>( images.size() );
        for ( Image image : images ) {
            list.add( toDto( image ) );
        }

        return list;
    }
}
