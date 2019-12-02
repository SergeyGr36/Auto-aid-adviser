package com.hillel.evo.adviser.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.MultipleFileUpload;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.hillel.evo.adviser.configuration.ImageConfigurationProperties;
import com.hillel.evo.adviser.dto.S3FileDTO;
import com.hillel.evo.adviser.exception.S3ServiceValidationException;
import com.hillel.evo.adviser.service.interfaces.CloudImageService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
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
    private static final TransferManager mockTransferManager = mock(TransferManager.class);
    private static final CloudImageService service =
            new S3CloudImageService(mockAmazonS3Client, mockTransferManager, mockProperties);
    private static final MultipleFileUpload mockUpload = mock(MultipleFileUpload.class);
    private static final MultipartFile mockFile = mock(MultipartFile.class);
    private static final S3FileDTO mockS3FileDTO = mock(S3FileDTO.class);
    private static final List<S3FileDTO> testS3FileDTOs = new ArrayList<>();
    private static final List<KeyVersion> testKeysToDelete = new ArrayList<>();
    private static final String testKeyFileName = "1/1/testFile.jpg";
    private static final String testDirectoryKeyPrefix = "1/1";
    private static final String testFileName = "testFile.jpg";
    private static final String testBucketName = "testBucket";
    private static final MultipartFile mockFileTest =
            new MockMultipartFile(testFileName, testFileName, MediaType.IMAGE_JPEG_VALUE, new byte[] {1});

    @BeforeAll
    static void setUp() {
        when(mockProperties.getExpiresIn()).thenReturn(1L);
        when(mockProperties.getBucketName()).thenReturn(testBucketName);
        when(mockProperties.getImageMaxSize()).thenReturn(5L);

        when(mockS3FileDTO.getFile()).thenReturn(mockFileTest);
        when(mockS3FileDTO.getKeyFileName()).thenReturn(testKeyFileName);
        when(mockS3FileDTO.getUniqFileName()).thenReturn(testFileName);

        testS3FileDTOs.add(mockS3FileDTO);
        testKeysToDelete.add(new KeyVersion(testKeyFileName));
    }

    @Test
    void whenUploadFileShouldUploadFile() {
        //given
        when(mockAmazonS3Client.putObject(any(PutObjectRequest.class))).thenReturn(null);
        //when
        boolean result = service.uploadFile(testKeyFileName, mockFileTest);
        //then
        assertTrue(result);
    }

    @Test
    void whenUploadFileShouldThrowException() {
        //given
        given(mockAmazonS3Client.putObject(any(PutObjectRequest.class))).willAnswer( invocation -> { throw new IOException("msg"); });
        //when
        boolean result = service.uploadFile(testKeyFileName, mockFile);
        //then
        assertFalse(result);
    }

    @Test
    void whenUploadFileShouldThrowEmptyFileException() throws S3ServiceValidationException {
        //given
        when(mockFile.isEmpty()).thenReturn(true);
        //when
        boolean result = service.uploadFile(testKeyFileName, mockFile);
        //then
        assertFalse(result);
    }

    @Test
    void whenUploadFileShouldThrowFileToBigException() throws S3ServiceValidationException {
        //given
        when(mockFile.getSize()).thenReturn(10L);
        //when
        boolean result = service.uploadFile(testKeyFileName, mockFile);
        //then
        assertFalse(result);
    }

    @Test
    void whenUploadFileShouldThrowIncorrectFileTypeException() throws S3ServiceValidationException {
        //given
        when(mockFile.getContentType()).thenReturn(MediaType.IMAGE_PNG_VALUE);
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
        boolean result = service.uploadFileList(testDirectoryKeyPrefix, testS3FileDTOs);
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
        boolean result = service.uploadFileList(testDirectoryKeyPrefix, testS3FileDTOs);
        //then
        assertFalse(result);
    }

    @Test
    void whenUploadFileListShouldThrowIOException() throws IOException {
        //given
        doThrow(IOException.class).when(mockFile).transferTo(any(Path.class));
        //when
        boolean result = service.uploadFileList(testDirectoryKeyPrefix, testS3FileDTOs);
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
    void whenDeleteFileListShodDeleteIt() {
        //given
        when(mockAmazonS3Client.deleteObjects(any(DeleteObjectsRequest.class))).thenReturn(null);
        //when
        boolean result = service.deleteFileList(testKeysToDelete);
        //then
        assertTrue(result);
    }

    @Test
    void whenDeleteFileListShouldThrowException() {
        //given
        given(mockAmazonS3Client.deleteObjects(any(DeleteObjectsRequest.class)))
                .willAnswer(invocation -> {throw new SdkClientException("test");});
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
    void whenGeneratePresignedURLShouldThrowException() {
        //given
        doThrow(new SdkClientException("Test"))
                .when(mockAmazonS3Client).generatePresignedUrl(any(GeneratePresignedUrlRequest.class));
        //when
        Optional<URL> result = service.generatePresignedURL(testKeyFileName);
        //then
        assertTrue(result.isEmpty());
    }
}