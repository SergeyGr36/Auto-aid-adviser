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

import java.net.MalformedURLException;
import java.net.URL;
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
    public void setUp() throws MalformedURLException {

        URL url = new URL("http", "localhost", "somefile");
        images = Arrays.asList(
                new Image(1L, "0/1/file1.jpg", "file1.jpg"),
                new Image(2L, "0/1/file2.jpg", "file2.jpg"),
                new Image(3L, "0/1/file3.jpg", "file3.jpg")
        );
        imageDtos = Arrays.asList(
                new ImageDto(1L, "0/1/file1.jpg", "file1.jpg", url),
                new ImageDto(2L, "0/1/file2.jpg", "file2.jpg", url),
                new ImageDto(3L, "0/1/file3.jpg", "file3.jpg", url)
        );
        image = images.get(0);
        dto = imageDtos.get(0);
    }

    @Test
    public void toListDtoThenEntityList() {
        //when
        List<ImageDto> resultDtos = imageMapper.toListDto(images);
        Assertions.assertEquals(resultDtos.size(), this.imageDtos.size());
        Assertions.assertEquals(resultDtos.get(0).getId(), this.imageDtos.get(0).getId());
        Assertions.assertEquals(resultDtos.get(0).getOriginalFileName(), this.imageDtos.get(0).getOriginalFileName());
        Assertions.assertEquals(resultDtos.get(0).getKeyFileName(), this.imageDtos.get(0).getKeyFileName());
    }

    @Test
    public void toListDtoThenEmptyList() {
        Assertions.assertEquals(imageMapper.toListDto(new ArrayList<>()).size(), 0);
    }

    @Test
    public void toListDtoThenNull() {
        Assertions.assertNull(imageMapper.toListDto(null));
    }

    @Test
    public void toDtoThenEntity() {
        //when
        ImageDto resultDto = imageMapper.toDto(image);
        //then
        Assertions.assertEquals(resultDto.getId(), this.dto.getId());
        Assertions.assertEquals(resultDto.getOriginalFileName(), this.dto.getOriginalFileName());
        Assertions.assertEquals(resultDto.getKeyFileName(), this.dto.getKeyFileName());
    }

    @Test
    public void toDtoThenNull() {
        Assertions.assertNull(imageMapper.toDto(null));
    }

    @Test
    public void toEntityThenDto() {
        Assertions.assertEquals(imageMapper.toEntity(dto), image);
    }

    @Test
    public void toEntityThenNull() {
        Assertions.assertNull(imageMapper.toEntity(null));
    }
}
