package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.ImageApplication;
import com.hillel.evo.adviser.dto.ImageDto;
import com.hillel.evo.adviser.entity.Image;
import com.hillel.evo.adviser.mapper.ImageMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ImageApplication.class})
public class ImageMapperTest {

    private List<Image> images;
    private List<ImageDto> imageDtos;
    private Image image;
    private ImageDto dto;

    @Autowired
    ImageMapper imageMapper;

    @BeforeEach
    public void setUp() {
        images = Arrays.asList(
                new Image(1L, "0/1/file1.jpg", "file1.jpg"),
                new Image(2L, "0/1/file2.jpg", "file2.jpg"),
                new Image(3L, "0/1/file3.jpg", "file3.jpg")
        );
        imageDtos = Arrays.asList(
                new ImageDto(1L, "0/1/file1.jpg", "file1.jpg"),
                new ImageDto(2L, "0/1/file2.jpg", "file2.jpg"),
                new ImageDto(3L, "0/1/file3.jpg", "file3.jpg")
        );
        image = images.get(0);
        dto = imageDtos.get(0);
    }

    @Test
    public void toListDtoWhenEntityList() {
        Assertions.assertEquals(imageMapper.toListDto(images), imageDtos);
    }

    @Test
    public void toListDtoWhenEmptyList() {
        Assertions.assertEquals(imageMapper.toListDto(new ArrayList<>()).size(), 0);
    }

    @Test
    public void toListDtoWhenNull() {
        Assertions.assertNull(imageMapper.toListDto(null));
    }

    @Test
    public void toDtoWhenEntity() {
        Assertions.assertEquals(imageMapper.toDto(image), dto);
    }

    @Test
    public void toDtoWhenNull() {
        Assertions.assertNull(imageMapper.toDto(null));
    }

    @Test
    public void toEntityWhenDto() {
        Assertions.assertEquals(imageMapper.toEntity(dto), image);
    }

    @Test
    public void toEntityWhenNull() {
        Assertions.assertNull(imageMapper.toEntity(null));
    }
}
