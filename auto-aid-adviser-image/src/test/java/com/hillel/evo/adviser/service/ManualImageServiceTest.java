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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Do the following before running the test methods
 * https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html
 */
@Disabled
@SpringBootTest(classes = {ImageApplication.class})
class ManualImageServiceTest {
    @Autowired
    private ImageService imageService;
    private static final Long testBusinessUserId = 777L;
    private static final Long testBusinessId = 2L;
    private static final Logger log = LoggerFactory.getLogger(ManualImageServiceTest.class);

    @Test
    void doCreate() throws Exception {
        MultipartFile file = getMultipartFile();
        Image image = imageService.create(testBusinessUserId, testBusinessId, file)
                .orElseThrow(() -> new RuntimeException("Failed"));
        log.warn(() -> "Key file name: " + image.getKeyFileName());
    }

    @Test
    void doCreateList() throws Exception{
        List<MultipartFile> testImages = getListMultipartFile();
        List<Image> list = imageService.create(testBusinessUserId,testBusinessId,testImages)
                .orElseThrow(()-> new RuntimeException("Failed"));
        log.warn(() -> "Fail to load list of images " + list.toString());
    }

    @Test
    void doGetPresignedUrl() throws Exception {
        MultipartFile file = getMultipartFile();
        //use key file name from doCreate() output
        String keyFileName = "777/1/ce3073e7-1193-4477-9f4d-81b7da19eb3f-Miner.jpg";
        Image image = new Image(keyFileName, file.getOriginalFilename());
        URL url = imageService.generatePresignedURL(image).orElseThrow(() -> new RuntimeException("Failed"));
        log.warn(() -> "Presigned url: " + url);
    }

    @Test
    void doDelete() throws Exception {
        MultipartFile file = getMultipartFile();
        //use key file name from doCreate() output
        String keyFileName = "777/1/0947d9eb-d5d0-444c-8b79-8a3bfcbd1ac4-Miner.jpg";
        Image imageToDelete = new Image(keyFileName, file.getOriginalFilename());
        assertTrue(imageService.delete(imageToDelete));
    }

    @Test
    void doDeleteList() throws Exception{
        List<MultipartFile> testImages = getListMultipartFile();
        String keyFileName1 = "999/1/ccc8fb4f-c70c-49c2-9064-ed92e851e130-test.jpg";
        String keyFileName2 = "999/1/f847e5f7-a0af-4c52-9151-946715008121-test.jpg";
        List<Image> imagesToDelete = new ArrayList<>();
        imagesToDelete.add(new Image(keyFileName1, testImages.get(0).getOriginalFilename()));
        imagesToDelete.add(new Image(keyFileName2, testImages.get(1).getOriginalFilename()));
        assertTrue(imageService.delete(imagesToDelete));
    }

    private MultipartFile getMultipartFile() throws IOException {
        String name = "Miner.jpg";
        Path path = Paths.get("F:/" + name);
        String contentType = MediaType.IMAGE_JPEG_VALUE;
        byte[] content = Files.readAllBytes(path);
        return new MockMultipartFile(name, name, contentType, content);
    }

    private List<MultipartFile> getListMultipartFile() throws IOException {
        List<MultipartFile> testImages = new ArrayList<>();
        String name = "test.jpg";
        Path path = Paths.get("C:\\Users\\ItsTi\\OneDrive\\Изображения\\" + name);
        String contentType = MediaType.IMAGE_JPEG_VALUE;
        byte[] content = Files.readAllBytes(path);
        testImages.add(new MockMultipartFile(name, name, contentType, content));
        testImages.add(new MockMultipartFile(name, name, contentType, content));
        return testImages;
    }
}
