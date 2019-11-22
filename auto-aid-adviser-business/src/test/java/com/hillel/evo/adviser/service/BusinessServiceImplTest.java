package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.BusinessApplication;
import com.hillel.evo.adviser.dto.BusinessDto;
import com.hillel.evo.adviser.dto.ContactDto;
import com.hillel.evo.adviser.dto.ImageDto;
import com.hillel.evo.adviser.dto.LocationDto;
import com.hillel.evo.adviser.dto.ServiceForBusinessShortDto;
import com.hillel.evo.adviser.dto.WorkTimeDto;
import com.hillel.evo.adviser.entity.ServiceForBusiness;
import com.hillel.evo.adviser.exception.CreateResourceException;
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

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        when(mockCloudImageService.deleteFile(any())).thenReturn(true);
        when(mockCloudImageService.uploadFile(any(), eq(goodFile))).thenReturn(true);
        when(mockCloudImageService.uploadFile(any(), eq(badFile))).thenReturn(false);
        when(mockCloudImageService.generatePresignedURL(any())).thenReturn(Optional.of(new URL("http", "localhost", "somefile")));
    }

    @Test
    public void whenCreateBusinessThenReturnDto() {
        //when
        BusinessDto saveDto = businessService.createBusiness(createTestDto(), userId);
        //then
        assertNotNull(saveDto);
        assertEquals(saveDto.getServiceForBusinesses().size(), 2);
    }

    @Test
    public void whenCreateBusinessWithFileThenReturnDto() {
        //when
        Optional<MultipartFile> multipartFile = Optional.of(goodFile);
        BusinessDto saveDto = businessService.createBusiness(createTestDto(), userId, multipartFile);
        //then
        assertNotNull(saveDto);
        assertEquals(saveDto.getServiceForBusinesses().size(), 2);
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
        //when
        ImageDto imageDto = businessService.addImage(userId, sourceDto.getId(), goodFile);
        //then
        assertEquals(imageDto.getOriginalFileName(), goodFile.getOriginalFilename());
    }

    @Test
    public void whenAddImageThenReturnNotFoundException() throws Exception {
        //then
        assertThrows(ResourceNotFoundException.class, () -> businessService.addImage(userId, 99L, goodFile));
    }

    @Test
    public void whenAddImageThenReturnCreateResourceException() {
        //given
        BusinessDto sourceDto = businessService.findAllByUser(userId).get(0);
        //then
        assertThrows(CreateResourceException.class, () -> businessService.addImage(userId, sourceDto.getId(), badFile));
    }

    @Test
    public void whenDeleteImageThenReturnTrue() {
        //given
        BusinessDto sourceDto = businessService.findAllByUser(userId).get(0);
        ImageDto dto = businessService.findImagesByBusinessId(sourceDto.getId()).get(0);
        //then
        assertTrue(businessService.deleteImage(dto));
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

/*
    private MultipartFile getMultipartFile() throws IOException {
        String name = "ny.jpg";
        Path path = Paths.get("C:/Temp/" + name);
        String contentType = MediaType.IMAGE_JPEG_VALUE;
        byte[] content = Files.readAllBytes(path);
        return new MockMultipartFile("file", name, contentType, content);
    }
*/

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
