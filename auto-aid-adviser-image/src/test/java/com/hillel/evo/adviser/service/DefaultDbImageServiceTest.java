package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.ImageApplication;
import com.hillel.evo.adviser.entity.Image;
import com.hillel.evo.adviser.service.interfaces.DbImageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {ImageApplication.class})
@Sql(value = {"/prepare-image.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class DefaultDbImageServiceTest {
    @Autowired
    private DbImageService imageService;
    private Long testId = 1L;
    private String testFileName = "filename.jpg";

    @Test
    void whenCreateImageShouldCreateImage() {
        //given
        Image imageToSave = new Image(testFileName, testFileName);
        //when
        Image savedImage = imageService.create(imageToSave);
        //then
        assertNotNull(savedImage.getId());
    }

    @Test
    void whenFindByIdShouldReturnId() {
        //when
        Image image = imageService.findById(testId).get();
        //then
        assertEquals(testId, image.getId());
    }

    @Test
    void update() {
        //given
        String newFileName = "newKeyFileName";
        Image image = new Image(testId, newFileName, testFileName);
        //when
        Image updated = imageService.update(image);
        //then
        assertEquals(newFileName, updated.getKeyFileName());
    }

    @Test
    void delete() {
        //given
        Image imageToDelete = new Image(testId, null, null);
        //when
        imageService.delete(imageToDelete);
        Image found = imageService.findById(testId).orElse(null);
        //then
        assertEquals(null, found);
    }
}