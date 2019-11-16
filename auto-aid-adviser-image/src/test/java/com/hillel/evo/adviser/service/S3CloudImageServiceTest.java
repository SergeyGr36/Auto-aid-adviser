package com.hillel.evo.adviser.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.hillel.evo.adviser.configuration.ImageConfigurationProperties;
import com.hillel.evo.adviser.service.interfaces.CloudImageService;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class S3CloudImageServiceTest {
    private static final AmazonS3 mockAmazonS3Client = mock(AmazonS3.class);
    private static final ImageConfigurationProperties mockProperties = mock(ImageConfigurationProperties.class);
    private static final String testKeyFileName = "1/1/testfile.jpg";
    private static final String testFileName = "testfile.jpg";
    MultipartFile mockFile = new MockMultipartFile
            (testFileName, testFileName, MediaType.IMAGE_JPEG_VALUE, new byte [] {0});
    private static final String testBucketName = "testBucket";

    private final CloudImageService service = new S3CloudImageService(mockAmazonS3Client, mockProperties);

    static {
        when(mockProperties.getExpiresIn()).thenReturn(1L);
        when(mockProperties.getBucketName()).thenReturn(testBucketName);
    }

    @Test
    void whenUploadFileShouldUploadFile() {
        //given
        when(mockAmazonS3Client.putObject(any(PutObjectRequest.class))).thenReturn(null);
        //when
        boolean result = service.uploadFile(testKeyFileName, mockFile);
        //then
        assertTrue(result);
    }

    @Test
    void whenUploadFileShouldThrowException() throws IOException {
        //given
        given(mockAmazonS3Client.putObject(any(PutObjectRequest.class))).willAnswer( invocation -> { throw new IOException("msg"); });
        //when
        boolean result = service.uploadFile(testKeyFileName, mockFile);
        //then
        assertFalse(result);
    }

    @Test
    void whenDeleteFileShouldDeleteFile() {
        //given
        doNothing().when(mockAmazonS3Client).deleteObject(any(DeleteObjectRequest.class));
        //when
        boolean result = service.deleteFile(testKeyFileName);
        //then
        assertTrue(result);
    }

    @Test
    void whenDeleteFileShouldThrowException() {
        //given
        doThrow(new SdkClientException("Test")).when(mockAmazonS3Client).deleteObject(any(DeleteObjectRequest.class));
        //when
        boolean result = service.deleteFile(testKeyFileName);
        //then
        assertFalse(result);
    }

    @Test
    void whenGeneratePresignedURLShouldDoIt() throws MalformedURLException {
        //given
        URL testURL = new URL("http", "localhost", "somefile");
        when(mockAmazonS3Client.generatePresignedUrl(any(GeneratePresignedUrlRequest.class))).thenReturn(testURL);
        //when
        Optional<URL> result = service.generatePresignedURL(testKeyFileName);
        //then
        assertEquals(testURL, result.get());
    }

    @Test
    void whenGeneratePresignedURLShouldThrowException() throws MalformedURLException {
        //given
        doThrow(new SdkClientException("Test"))
                .when(mockAmazonS3Client).generatePresignedUrl(any(GeneratePresignedUrlRequest.class));
        //when
        Optional<URL> result = service.generatePresignedURL(testKeyFileName);
        //then
        assertTrue(result.isEmpty());
    }
}