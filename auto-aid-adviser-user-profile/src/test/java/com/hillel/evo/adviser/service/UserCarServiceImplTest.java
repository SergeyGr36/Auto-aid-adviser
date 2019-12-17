package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.UserProfileStarter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.hillel.evo.adviser.dto.ImageDto;
import com.hillel.evo.adviser.dto.UserCarDto;
import com.hillel.evo.adviser.entity.UserCar;
import com.hillel.evo.adviser.entity.identification.CarModel;
import com.hillel.evo.adviser.mapper.UserCarMapper;
import com.hillel.evo.adviser.repository.AdviserUserDetailRepository;
import com.hillel.evo.adviser.repository.CarIdentificationRepository;
import com.hillel.evo.adviser.service.impl.UserCarServiceImpl;
import com.hillel.evo.adviser.service.interfaces.CloudImageService;
import org.junit.jupiter.api.Assertions;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserProfileStarter.class)
@Sql(value = {"/user-profile.sql", "/create-image.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/clean-user-profile.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserCarServiceImplTest {

    private Long userId;
    private UserCarDto dto;

    @Autowired
    private UserCarServiceImpl service;
    @Autowired
    private AdviserUserDetailRepository userRepository;
    @Autowired
    private UserCarMapper mapper;

    @Autowired
    private CarIdentificationRepository carIdentificationRepo;

    @MockBean
    CloudImageService mockCloudImageService;

    MultipartFile goodFile;
    MultipartFile badFile;
    @BeforeEach
    private void init() throws Exception{
        userId = userRepository.findByEmail("svg@mail.com").get().getId();
        goodFile = getGoodMultipartFile();
        badFile = getBadMultipartFile();

        when(mockCloudImageService.hasDeletedFile(any())).thenReturn(true);
        when(mockCloudImageService.hasUploadedFile(any(), eq(goodFile))).thenReturn(true);
        when(mockCloudImageService.hasUploadedFile(any(), eq(badFile))).thenReturn(false);
        when(mockCloudImageService.generatePresignedURL(any())).thenReturn(Optional.of(new URL("http", "localhost", "somefile")));
    }

    @Test
    public void whenCreateUserCarThenReturnDto(){
        CarModel carModel = carIdentificationRepo.findCarModelByName("M5");
        UserCarDto newDto = new UserCarDto();
        //newDto.setCarModel(carModel);

        UserCarDto testDto = service.createUserCar(newDto, userId);
       assertNotNull(testDto.getId());
    }

    @Test
    @Transactional
    public void whenUpdateUserCarThenReturnDto(){
        List<UserCarDto> list = service.getByUserId(userId);
        dto = list.get(0);
        UserCarDto testDto = service.updateUserCar(dto, userId);
        assertEquals(dto.getId(), testDto.getId());
    }
    @Test
    public void whenGetCarByUserIdAndCarIdThenReturnDto(){
        UserCar car = new UserCar();
        UserCarDto actual = service.createUserCar(mapper.toDto(car), userId);
        UserCarDto expected = service.getCarByUserIdAndCarId(userId, actual.getId());
        assertEquals(actual.getId(), expected.getId());
    }
    @Test
    @Transactional
    public void whenDeleteUserCarThenThrowException(){
        assertThrows(RuntimeException.class, ()-> service.deleteUserCar(100L, userId) );
    }
    @Test
    public void whenDeleteUserCarThenSuccess(){
        UserCarDto dto = service.createUserCar(new UserCarDto(), userId);
      Assertions.assertDoesNotThrow(()->service.deleteUserCar(dto.getId(), userId));

    }
    @Test
    @Transactional
    public void whenAddImageThenReturnDto(){
        UserCarDto dto = service.getByUserId(userId).get(0);
        ImageDto imageDto = service.addImage(userId, dto.getId(), goodFile);
        assertEquals(imageDto.getOriginalFileName(), goodFile.getOriginalFilename());
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
