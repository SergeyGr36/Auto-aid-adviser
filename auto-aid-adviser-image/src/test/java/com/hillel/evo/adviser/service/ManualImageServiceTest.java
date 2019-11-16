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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html
 */
@Disabled
@SpringBootTest(classes = {ImageApplication.class})
public class ManualImageServiceTest {
    @Autowired
    ImageService imageService;
    final Long testBusinessUserId = 777L;
    final Long testBusinessId = 1L;

    @Test
    public void doCreate() throws Exception {
        MultipartFile file = getMultipartFile();
        imageService.create(testBusinessUserId, testBusinessId, file)
                .orElseThrow(() -> new RuntimeException("Failed"));
    }

    @Test
    public void doDelete() throws Exception {
        MultipartFile file = getMultipartFile();
        String keyFileName = imageService.generateKeyFileName(testBusinessUserId, testBusinessId, file);
        Image imageToDelete = new Image(keyFileName, file.getOriginalFilename());
        assertTrue(imageService.delete(imageToDelete));
    }

    private MultipartFile getMultipartFile() throws IOException {
        String name = "Miner.jpg";
        Path path = Paths.get("F:/" + name);
        String contentType = MediaType.IMAGE_JPEG_VALUE;
        byte[] content = Files.readAllBytes(path);
        return new MockMultipartFile(name, name, contentType, content);
    }
}
