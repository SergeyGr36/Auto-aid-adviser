package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.UserProfileStarter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.hillel.evo.adviser.dto.CarModelDto;
import com.hillel.evo.adviser.dto.ImageDto;
import com.hillel.evo.adviser.dto.UserCarDto;
import com.hillel.evo.adviser.entity.UserCar;
import com.hillel.evo.adviser.entity.CarModel;
import com.hillel.evo.adviser.mapper.UserCarMapper;
import com.hillel.evo.adviser.repository.AdviserUserDetailRepository;
import com.hillel.evo.adviser.repository.CarModelRepository;
import com.hillel.evo.adviser.repository.TypeCarRepository;
import com.hillel.evo.adviser.repository.UserCarRepository;
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
    private UserCarRepository userCarRepository;

    @Autowired
    private CarModelService carModelService;

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
        //given
        UserCarDto newDto = getNewUserCarDto();
        //when
        UserCarDto testDto = service.createUserCar(newDto, userId);
        //then
        assertNotNull(testDto.getId());
        assertEquals(newDto.getCarModel(), testDto.getCarModel());
    }

    @Test
    public void whenUpdateUserCarThenReturnDto(){
        //given
        int newYear = 2025;
        UserCarDto oldDto = service.getByUserId(userId).get(0);
        oldDto.setReleaseYear(newYear);
        //when
        UserCarDto updateDto = service.updateUserCar(oldDto, userId);
        UserCar car = userCarRepository.findById(updateDto.getId()).get();
        //then
        assertEquals(oldDto.getId(), updateDto.getId());
        assertEquals(newYear, updateDto.getReleaseYear());
        assertEquals(newYear, car.getReleaseYear());
    }

    @Test
    public void whenGetCarByUserIdAndCarIdThenReturnDto() {
        //given
        UserCar car = userCarRepository.findAllBySimpleUserId(userId).get(0);
        //when
        UserCarDto dto = service.getCarByUserIdAndCarId(userId, car.getId());
        //then
        assertEquals(car.getId(), dto.getId());
        assertEquals(car.getReleaseYear(), dto.getReleaseYear());
    }

    @Test
    public void whenGetCarByUserIdThenReturnListDto() {
        List<UserCarDto> list = service.getByUserId(userId);
        //then
        assertEquals(2, list.size());
    }

    @Test
    public void whenDeleteUserCarThenThrowException() {
        assertThrows(RuntimeException.class, ()-> service.deleteUserCar(100L, userId) );
    }

    @Test
    public void whenDeleteUserCarThenSuccess(){
        List<UserCar> userCars = userCarRepository.findAllBySimpleUserId(userId);
        long carId = userCars.get(0).getId();
        Assertions.assertDoesNotThrow(()->service.deleteUserCar(carId, userId));

    }
    @Test
    @Transactional
    public void whenAddImageThenReturnDto(){
        UserCarDto dto = service.getByUserId(userId).get(0);
        ImageDto imageDto = service.addImage(userId, dto.getId(), goodFile);
        assertEquals(imageDto.getOriginalFileName(), goodFile.getOriginalFilename());
    }

    private UserCarDto getNewUserCarDto() {
        CarModelDto carModelDto = carModelService.findByName("M5");
        UserCarDto userCarDto = new UserCarDto();
        userCarDto.setCarModel(carModelDto);
        userCarDto.setDescription("some description");
        userCarDto.setIndividualCarNaming("AA123456");
        userCarDto.setReleaseYear(2016);

        return userCarDto;
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
