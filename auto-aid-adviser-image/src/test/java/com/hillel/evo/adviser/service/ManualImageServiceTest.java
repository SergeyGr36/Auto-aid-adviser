package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.ImageApplication;
import com.hillel.evo.adviser.entity.Image;
import com.hillel.evo.adviser.service.interfaces.ImageService;
import org.junit.jupiter.api.BeforeAll;
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
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Do the following before running the test methods:
 *
 * - https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html
 * - setup a {@link #setUp()} method before launch test methods
 * - run this test methods manually one by one and follow the comments in every test methods
 */

@Disabled
@SpringBootTest(classes = {ImageApplication.class})
class ManualImageServiceTest {
    @Autowired
    private ImageService imageService;
    private static final Long testBusinessUserId = 777L;
    private static final Long testBusinessId = 2L;
    private static final Logger log = LoggerFactory.getLogger(ManualImageServiceTest.class);
    private static MultipartFile file;
    private static List<MultipartFile> listFiles;

    @BeforeAll
    static void setUp() throws IOException {
        //use file name of your own test file
        String name = "test.jpg";
        //use the real path to your test file
        Path path = Paths.get("C:\\" + name);
        String contentType = MediaType.IMAGE_JPEG_VALUE;
        byte[] content = Files.readAllBytes(path);
        file = new MockMultipartFile(name, name, contentType, content);
        listFiles = Collections.singletonList(file);
    }

    @Test
    void doCreate() {
        Image image = imageService.create(testBusinessUserId, testBusinessId, file)
                .orElseThrow(() -> new RuntimeException("Failed"));
        log.warn(() -> "Key file name: " + image.getKeyFileName());
    }

    @Test
    void doCreateList() {
        List<Image> list = imageService.create(testBusinessUserId, testBusinessId, listFiles)
                .orElseThrow(()-> new RuntimeException("Failed"));
        log.warn(() -> "Key file names: " + list.toString());
    }

    @Test
    void doGetPresignedUrl() {
        //use key file name from doCreate() output
        String keyFileName = "777/2/349c7c98-395e-4f31-ba02-0777e02a5d4a-test.jpg";
        Image image = new Image(keyFileName, file.getOriginalFilename());
        URL url = imageService.generatePresignedURL(image).orElseThrow(() -> new RuntimeException("Failed"));
        log.warn(() -> "Presigned url: " + url);
    }

    @Test
    void doDelete() {
        //use key file name from doCreate() output
        String keyFileName = "777/2/349c7c98-395e-4f31-ba02-0777e02a5d4a-test.jpg";
        Image imageToDelete = new Image(keyFileName, file.getOriginalFilename());
        assertTrue(imageService.delete(imageToDelete));
    }

    @Test
    void doDeleteList() {
        //use key file name from doCreateList() output
        String keyFileName = "777/2/d784934b-385e-4568-918f-65390eca0f7f-test.jpg";
        List<Image> imagesToDelete = new ArrayList<>();
        imagesToDelete.add(new Image(keyFileName, listFiles.get(0).getOriginalFilename()));
        assertTrue(imageService.delete(imagesToDelete));
    }

}
