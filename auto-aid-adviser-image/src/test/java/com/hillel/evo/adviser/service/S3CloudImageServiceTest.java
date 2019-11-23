package com.hillel.evo.adviser.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.MultipleFileUpload;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.hillel.evo.adviser.configuration.ImageConfigurationProperties;
import com.hillel.evo.adviser.exception.S3ServiceValidationException;
import com.hillel.evo.adviser.service.interfaces.CloudImageService;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    private static final TransferManager mockTransferManager = mock(TransferManager.class);
    private static final MultipleFileUpload mockUpload = mock(MultipleFileUpload.class);
    private static final String testKeyFileName = "1/1/testfile.jpg";
    private static final String testFileName = "testfile.jpg";
    private static final String testBucketName = "testBucket";
    private static final Long testBusinessUserId = 1L;
    private static final Long testBusinessId = 1L;
    private static final List<String> testKeyFileNames = new ArrayList<>();
    private static final List<MultipartFile> testFiles = new ArrayList<>();
    private final CloudImageService service =
            new S3CloudImageService(mockAmazonS3Client, mockTransferManager, mockProperties);

    private static final MultipartFile mockFile = new MockMultipartFile
            (testFileName, testFileName, MediaType.IMAGE_JPEG_VALUE, new byte [] {1});

    private static final MultipartFile mockInvalidEmptyFile = new MockMultipartFile
            (testFileName, testFileName, MediaType.IMAGE_JPEG_VALUE, new byte [] {});

    private static final MultipartFile mockInvalidSizeFile = new MockMultipartFile
            (testFileName, testFileName, MediaType.IMAGE_JPEG_VALUE, new byte [] {0,1,2,3,4,5,6,7,8,9});

    private static final MultipartFile mockInvalidTypeFile = new MockMultipartFile
            (testFileName, testFileName, MediaType.IMAGE_PNG_VALUE, new byte [] {1});

    static {
        when(mockProperties.getExpiresIn()).thenReturn(1L);
        when(mockProperties.getBucketName()).thenReturn(testBucketName);
        when(mockProperties.getImageMaxSize()).thenReturn(5L);
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
    void whenUploadFileListShouldUploadIt() throws Exception {
        //given
        when(mockTransferManager.uploadDirectory(
                any(String.class),
                any(String.class),
                any(File.class),
                any(Boolean.class))).thenReturn(mockUpload);
        doNothing().when(mockUpload).waitForCompletion();
        //when
        boolean result = service.uploadFileList(testBusinessUserId, testBusinessId, testKeyFileNames, testFiles);
        //then
        assertTrue(result);
    }

    @Test
    void whenUploadFileListShouldThrowException() {
        //given
        given(mockTransferManager.uploadDirectory(
                any(String.class),
                any(String.class),
                any(File.class),
                any(Boolean.class))).willAnswer(invocation -> {throw new SdkClientException("msg");});
        //when
        boolean result = service.uploadFileList(testBusinessUserId, testBusinessId, testKeyFileNames, testFiles);
        //then
        assertFalse(result);
    }

    @Test
    void whenUploadFileShouldThrowEmptyFileException() throws S3ServiceValidationException{
        assertThrows(S3ServiceValidationException.class, ()->service.uploadFile(testKeyFileName, mockInvalidEmptyFile));
    }

    @Test
    void whenUploadFileShouldThrowFileToBigException() throws S3ServiceValidationException{
        assertThrows(S3ServiceValidationException.class, ()->service.uploadFile(testKeyFileName, mockInvalidSizeFile));
    }

    @Test
    void whenUploadFileShouldThrowIncorrectFileTypeException() throws S3ServiceValidationException{
         assertThrows(S3ServiceValidationException.class, ()->service.uploadFile(testKeyFileName, mockInvalidTypeFile));
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