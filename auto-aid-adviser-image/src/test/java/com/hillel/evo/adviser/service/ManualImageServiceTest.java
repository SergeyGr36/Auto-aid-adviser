package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.ImageApplication;
import com.hillel.evo.adviser.entity.Image;
import com.hillel.evo.adviser.service.interfaces.ImageService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Disabled
@SpringBootTest(classes = {ImageApplication.class})
public class ManualImageServiceTest {
    @Autowired
    ImageService imageService;

    @Test
    public void doCreate() throws Exception {
        Long businessUserId = 1L;
        Long businessId = 1L;
        imageService.create(businessUserId, businessId, getMultipartFile())
                .orElseThrow(() -> new RuntimeException("Failed"));
    }

    private MultipartFile getMultipartFile() throws IOException {
        String name = "Miner.jpg";
        String originalFileName = "47048011_379023389502996_4711337544686501888_n.jpg";
        Path path = Paths.get("E:/" + originalFileName);
        String contentType = MediaType.IMAGE_JPEG_VALUE;
        byte[] content = null;
        content = Files.readAllBytes(path);
        MultipartFile result = new MockMultipartFile(name,
                originalFileName, contentType, content);
        return result;
    }
}
