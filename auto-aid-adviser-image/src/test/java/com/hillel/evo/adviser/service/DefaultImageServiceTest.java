package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.entity.Image;
import com.hillel.evo.adviser.repository.ImageRepository;
import com.hillel.evo.adviser.service.interfaces.CloudImageService;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class DefaultImageServiceTest {
    private static final CloudImageService mockCloudImageService = mock(CloudImageService.class);
    private static final ImageRepository mockDbImageRepository = mock(ImageRepository.class);

    private static final Long testBusinessUserId = 1L;
    private static final Long testBusinessId = 1L;
    private static final String testKeyFileName = "1/1/testfile.jpg";
    private static final String testFileName = "testfile.jpg";
    private static final Image testImage = new Image(testKeyFileName, testFileName);
    private static MultipartFile mockFile = new MockMultipartFile
            (testFileName, testFileName, MediaType.IMAGE_JPEG_VALUE, new byte [] {0});

    private final DefaultImageService service = new DefaultImageService(mockCloudImageService, mockDbImageRepository);

    @Test
    void whenCreateImageShouldCreateIt() {
        //given
        when(mockCloudImageService.uploadFile(any(String.class), eq(mockFile))).thenReturn(true);
        when(mockDbImageRepository.save(any(Image.class))).thenReturn(testImage);
        //when
        Optional<Image> result = service.create(testBusinessUserId, testBusinessId, mockFile);
        //then
        assertEquals(testImage, result.get());
    }

    @Test
    void whenCreateImageShouldNotCreateIt() {
        //given
        when(mockCloudImageService.uploadFile(any(String.class), eq(mockFile))).thenReturn(false);
        //when
        Optional<Image> result = service.create(testBusinessUserId, testBusinessId, mockFile);
        //then
        assertTrue(result.isEmpty());
    }

    @Test
    void whenDeleteImageShouldDeleteIt() {
        //given
        when(mockCloudImageService.deleteFile(testKeyFileName)).thenReturn(true);
        //when
        boolean result = service.delete(testImage);
        //then
        assertTrue(result);
    }

    @Test
    void whenDeleteImageShouldNotDeleteIt() {
        //given
        when(mockCloudImageService.deleteFile(testKeyFileName)).thenReturn(false);
        //when
        boolean result = service.delete(testImage);
        //then
        assertFalse(result);
    }

    @Test
    void whenGeneratePresignedURLShouldDoIt() throws MalformedURLException {
        //given
        URL testURL = new URL("http", "localhost", "somefile");
        when(mockCloudImageService.generatePresignedURL(testKeyFileName)).thenReturn(Optional.of(testURL));
        //when
        Optional<URL> result = service.generatePresignedURL(testImage);
        //then
        assertEquals(testURL, result.get());
    }
}