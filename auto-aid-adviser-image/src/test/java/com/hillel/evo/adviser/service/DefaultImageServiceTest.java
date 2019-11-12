package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.ImageApplication;
import com.hillel.evo.adviser.entity.Image;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {ImageApplication.class})
@Sql(value = {"/prepare-image.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class DefaultImageServiceTest {
    @Autowired
    private ImageService imageService;

    @Test
    void whenCreateImageShouldCreateImage() {
        Image imageToSave = new Image("filename.jpg");

        Image savedImage = imageService.create(imageToSave);

        assertNotNull(savedImage.getId());
    }

    @Test
    void findAllByBusiness() {
    }

    @Test
    void findById() {

    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}