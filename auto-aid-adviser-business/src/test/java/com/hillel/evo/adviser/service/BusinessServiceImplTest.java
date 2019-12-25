package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.BusinessApplication;
import com.hillel.evo.adviser.configuration.HibernateSearchConfig;
import com.hillel.evo.adviser.dto.*;
import com.hillel.evo.adviser.entity.ServiceForBusiness;
import com.hillel.evo.adviser.entity.ServiceType;
import com.hillel.evo.adviser.exception.ResourceNotFoundException;
import com.hillel.evo.adviser.repository.AdviserUserDetailRepository;
import com.hillel.evo.adviser.repository.ServiceForBusinessRepository;
import com.hillel.evo.adviser.service.impl.BusinessServiceImpl;
import com.hillel.evo.adviser.service.interfaces.CloudImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {BusinessApplication.class})
@Sql(value = {"/create-user.sql", "/create-business.sql", "/create-image.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/clean-image.sql", "/clean-business.sql", "/clean-user.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class BusinessServiceImplTest {
@Autowired
    HibernateSearchConfig config;
    @Autowired
    private BusinessServiceImpl businessService;

    @Autowired
    private AdviserUserDetailRepository repository;

    @Autowired
    private ServiceForBusinessRepository serviceForBusinessRepository;

    @MockBean
    CloudImageService mockCloudImageService;

    Long userId;

    MultipartFile goodFile;
    MultipartFile badFile;

    @BeforeEach
    private void init() throws Exception {
        goodFile = getGoodMultipartFile();
        badFile = getBadMultipartFile();
        userId = repository.findByEmail("bvg@mail.com").get().getId();

        when(mockCloudImageService.hasDeletedFile(any())).thenReturn(true);
        when(mockCloudImageService.hasUploadedFile(any(), eq(goodFile))).thenReturn(true);
        when(mockCloudImageService.hasUploadedFile(any(), eq(badFile))).thenReturn(false);
        when(mockCloudImageService.hasUploadedFileList(any(), any(List.class))).thenReturn(true);
        when(mockCloudImageService.generatePresignedURL(any())).thenReturn(Optional.of(new URL("http", "localhost", "somefile")));
    }

    @Test
    public void whenCreateBusinessThenReturnDto() {
        //when
        BusinessDto saveDto = businessService.createBusiness(createTestDto(), userId);
        //then
        assertNotNull(saveDto);
        assertEquals(2, saveDto.getServiceForBusinesses().size());
        assertEquals(6, saveDto.getWorkTimes().size());
    }

    @Test
    public void whenCreateBusinessWithListFilesThenReturnDto() {
        //when
        List<MultipartFile> listFiles = new ArrayList<>();
        listFiles.add(goodFile);
        listFiles.add(goodFile);
        listFiles.add(goodFile);
        listFiles.add(goodFile);
        BusinessDto saveDto = businessService.createBusiness(createTestDto(), userId, listFiles);
        //then
        assertNotNull(saveDto);
        assertEquals(2, saveDto.getServiceForBusinesses().size());
        assertEquals(6, saveDto.getWorkTimes().size());
        assertEquals(listFiles.size(), businessService.findImagesByBusinessId(saveDto.getId()).size());
    }

    @Test
    public void whenCreateBusinessThenReturnThrow() {
        assertThrows(Exception.class, () -> businessService.createBusiness(new BusinessDto(), userId));
    }

    @Test
    public void whenFindByUserIdThenReturnListBusinessDto() {
        //when
        List<BusinessDto> list = businessService.findAllByUser(userId);
        //then
        assertEquals(list.size(), 2);
    }

    @Test
    public void whenFindByBusinessIdThenReturnBusinessDto() {
        //given
        BusinessDto dto = businessService.findAllByUser(userId).get(0);
        //when
        BusinessDto findBusinessDto = businessService.findBusinessById(dto.getId(), userId);
        //then
        assertEquals(findBusinessDto.getId(), dto.getId());
        assertEquals(findBusinessDto.getName(), dto.getName());
        assertEquals(findBusinessDto.getServiceForBusinesses().size(), dto.getServiceForBusinesses().size());
    }

    @Test
    public void whenFindByBusinessIdThenReturnException() {
        assertThrows(ResourceNotFoundException.class, () -> businessService.findBusinessById(99L, userId));
    }



    @Test
    public void whenUpdateBusinessThenReturnBusinessDto() {
        //given
        BusinessDto sourceDto = businessService.findAllByUser(userId).get(0);
        sourceDto.setName("updating name");
        sourceDto.getServiceForBusinesses().remove(0);
        sourceDto.getWorkTimes().add(new WorkTimeDto(7, LocalTime.of(11, 00), LocalTime.of(20, 00)));
        //when
        BusinessDto updateDto = businessService.updateBusiness(sourceDto, userId);
        //then
        assertEquals(updateDto.getId(), sourceDto.getId());
        assertEquals(updateDto.getName(), sourceDto.getName());
        assertEquals(updateDto.getServiceForBusinesses().size(), sourceDto.getServiceForBusinesses().size());
    }

    @Test
    public void whenDeleteBusinessThenNotThrow() {
        BusinessDto sourceDto = businessService.findAllByUser(userId).get(0);
        assertDoesNotThrow(() -> businessService.deleteBusiness(sourceDto.getId(), userId));
    }

    @Test
    public void whenDeleteBusinessThenReturnException() {
        assertThrows(Exception.class, () -> businessService.deleteBusiness(99L, userId));
    }

    @Test
    public void whenAddImageThenReturnImageDto() {
        //given
        BusinessDto sourceDto = businessService.findAllByUser(userId).get(0);
        List<MultipartFile> list = new ArrayList<>();
        list.add(goodFile);
        //when
        List<ImageDto> listDto = businessService.addImages(userId, sourceDto.getId(), list);
        //then
        assertEquals(listDto.get(0).getOriginalFileName(), goodFile.getOriginalFilename());
    }

    @Test
    public void whenAddImageThenReturnNotFoundException() {
        //given
        List<MultipartFile> list = new ArrayList<>();
        list.add(goodFile);
        //then
        assertThrows(ResourceNotFoundException.class, () -> businessService.addImages(userId, 99L, list));
    }

    @Test
    public void whenAddImageThenReturnCreateResourceException() {
        //given
        BusinessDto sourceDto = businessService.findAllByUser(userId).get(0);
        List<MultipartFile> list = new ArrayList<>();
        list.add(badFile);
        //then
        assertThrows(Exception.class, () -> businessService.addImages(userId, sourceDto.getId(), null));
    }

    @Test
    public void whenDeleteImageThenReturnTrue() {
        //given
        BusinessDto sourceDto = businessService.findAllByUser(userId).get(0);
        ImageDto dto = businessService.findImagesByBusinessId(sourceDto.getId()).get(0);
        //then
        assertTrue(businessService.deleteImage(userId, sourceDto.getId(), dto.getId()));
    }

    @Test
    public void whenDeleteImageThenReturnException() {
        //given
        BusinessDto sourceDto = businessService.findAllByUser(userId).get(0);
        ImageDto dto = businessService.findImagesByBusinessId(sourceDto.getId()).get(0);
        //then
        assertThrows(ResourceNotFoundException.class, () -> businessService.deleteImage(99L, 99L, dto.getId()));
    }

    @Test
    public void whenAllImageByBusinessIdThenReturnSetImages() {
        //given
        BusinessDto sourceDto = businessService.findAllByUser(userId).get(0);
        //when
        List<ImageDto> images = businessService.findImagesByBusinessId(sourceDto.getId());
        //then
        assertEquals(1, images.size());
    }

    @Test
    public void whenCreateTemplateBusinessThenReturnDto() {
        //when
        BusinessFullDto templateBusiness = businessService.createTemplateBusiness();
        //then
        assertNotNull(templateBusiness);
        assertEquals(7, templateBusiness.getWorkTimes().size());
        assertFalse(templateBusiness.getServiceForBusinesses().isEmpty());
    }

@Test
public void whenfindByBusinessTypeServiceTypeLocationReturnBusinessDto(){
           config.reindex(ServiceType.class);
            String ServiceType = "ServiceType";
            double longtitude = 134;
            double latitude = 132;
            var result = businessService.findByBusinessTypeServiceTypeLocation(ServiceType,longtitude,latitude);
            assertEquals(result.size(),1);
    /* List<BusinessDto> businessDto = businessService.findByBusinessTypeServiceTypeLocation(serviceForBusiness,longtitude,latitude);
     assertEquals(businessDto.size(),1);*/
}

    private BusinessDto createTestDto() {
        List<ServiceForBusiness> list = serviceForBusinessRepository.findAll();

        BusinessDto dto = new BusinessDto();
        dto.setName("some name");

        LocationDto location = new LocationDto();
        location.setLatitude(99);
        location.setLongitude(99);
        location.setAddress("some address");

        ContactDto contact = new ContactDto();
        contact.setPhone("2345678");

        ServiceForBusinessShortDto serviceBusiness1 = new ServiceForBusinessShortDto();
        serviceBusiness1.setId(list.get(0).getId());

        ServiceForBusinessShortDto serviceBusiness2 = new ServiceForBusinessShortDto();
        serviceBusiness2.setId(list.get(1).getId());

        dto.setContact(contact);
        dto.setLocation(location);
        dto.setServiceForBusinesses(Arrays.asList(serviceBusiness1, serviceBusiness2));

        Set<WorkTimeDto> times = new HashSet<WorkTimeDto>();
        times.add(new WorkTimeDto(1, LocalTime.of(8, 30), LocalTime.of(18, 30)));
        times.add(new WorkTimeDto(2, LocalTime.of(8, 30), LocalTime.of(18, 30)));
        times.add(new WorkTimeDto(3, LocalTime.of(8, 30), LocalTime.of(18, 30)));
        times.add(new WorkTimeDto(4, LocalTime.of(8, 30), LocalTime.of(18, 30)));
        times.add(new WorkTimeDto(5, LocalTime.of(8, 30), LocalTime.of(18, 30)));
        times.add(new WorkTimeDto(6, LocalTime.of(8, 30), LocalTime.of(18, 30)));
        dto.setWorkTimes(times);

        return dto;
    }

    private MultipartFile getGoodMultipartFile() {
        String contentType = MediaType.IMAGE_JPEG_VALUE;
        byte[] content = {11, 12, 13, 14, 15};
        return new MockMultipartFile("file", "file.good", contentType, content);
    }

    private MultipartFile getBadMultipartFile() {
        String contentType = MediaType.IMAGE_JPEG_VALUE;
        byte[] content = {1, 2, 3, 4, 5};
        return new MockMultipartFile("file", "file.bad", contentType, content);
    }


}
