package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.ImageApplication;
import com.hillel.evo.adviser.entity.Image;
import com.hillel.evo.adviser.service.interfaces.ImageService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Do the following before running the test methods
 * https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html
 */
@Disabled
@SpringBootTest(classes = {ImageApplication.class})
public class ManualImageServiceTest {
    @Autowired
    ImageService imageService;
    final Long testBusinessUserId = 777L;
    final Long testBusinessId = 1L;
    final Logger log = LoggerFactory.getLogger(ManualImageServiceTest.class);

    @Test
    public void doCreate() throws Exception {
        MultipartFile file = getMultipartFile();
        Image image = imageService.create(testBusinessUserId, testBusinessId, file)
                .orElseThrow(() -> new RuntimeException("Failed"));
        log.warn(() -> "Key file name: " + image.getKeyFileName());
    }

    @Test
    public void doGetPresignedUrl() throws Exception {
        MultipartFile file = getMultipartFile();
        //use key file name from doCreate() output
        String keyFileName = "777/1/ce3073e7-1193-4477-9f4d-81b7da19eb3f-Miner.jpg";
        Image image = new Image(keyFileName, file.getOriginalFilename());
        URL url = imageService.generatePresignedURL(image).orElseThrow(() -> new RuntimeException("Failed"));
        log.warn(() -> "Presigned url: " + url);
    }

    @Test
    public void doDelete() throws Exception {
        MultipartFile file = getMultipartFile();
        //use key file name from doCreate() output
        String keyFileName = "777/1/0947d9eb-d5d0-444c-8b79-8a3bfcbd1ac4-Miner.jpg";
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
