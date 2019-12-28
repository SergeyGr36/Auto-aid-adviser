package com.hillel.evo.adviser.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillel.evo.adviser.AdviserStarter;
import com.hillel.evo.adviser.dto.CarModelDto;
import com.hillel.evo.adviser.dto.SimpleUserDto;
import com.hillel.evo.adviser.dto.UserCarDto;
import com.hillel.evo.adviser.entity.AdviserUserDetails;
import com.hillel.evo.adviser.entity.CarModel;
import com.hillel.evo.adviser.entity.UserCar;
import com.hillel.evo.adviser.repository.AdviserUserDetailRepository;
import com.hillel.evo.adviser.repository.CarModelRepository;
import com.hillel.evo.adviser.repository.UserCarRepository;
import com.hillel.evo.adviser.service.CarModelService;
import com.hillel.evo.adviser.service.EncoderService;
import com.hillel.evo.adviser.service.JwtService;
import com.hillel.evo.adviser.service.UserCarService;
import com.hillel.evo.adviser.service.interfaces.CloudImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.endsWith;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AdviserStarter.class)
@AutoConfigureMockMvc
@Sql(value = {"/clean-user-profile.sql", "/clean-business.sql", "/clean-user.sql",
        "/create-user2.sql", "/create-business.sql", "/user-profile.sql", "/create-image.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/clean-user-profile.sql", "/clean-business.sql", "/clean-user.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserProfileControllerTest {

    private AdviserUserDetails user;
    private String jwt;

    private static final String USER_EMAIL = "svg@mail.com";

    private static final String PATH = "/user/profile";

    @Autowired
    private AdviserUserDetailRepository userRepository;

    @Autowired
    private CarModelRepository carModelRepository;

    @Autowired
    private UserCarRepository userCarRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    JwtService jwtService;

    @Autowired
    EncoderService encoderService;

    @Autowired
    private CarModelService carModelService;

    @Autowired
    private UserCarService userCarService;

    @MockBean
    CloudImageService mockCloudImageService;

    @BeforeEach
    public void setUp() throws MalformedURLException {

        encodeTestUserPassword();
        user = userRepository.findByEmail(USER_EMAIL).get();
        jwt = jwtService.generateAccessToken(user.getId());

        when(mockCloudImageService.hasDeletedFile(endsWith(".jpg"))).thenReturn(true);
        when(mockCloudImageService.hasDeletedFile(endsWith(".bad"))).thenReturn(false);
        when(mockCloudImageService.hasUploadedFile(any(), any())).thenReturn(true);
        when(mockCloudImageService.hasUploadedFileList(any(), any(List.class))).thenReturn(true);
        when(mockCloudImageService.generatePresignedURL(any())).thenReturn(Optional.of(new URL("http", "localhost", "somefile")));
    }

    private void encodeTestUserPassword() {
        AdviserUserDetails user = userRepository.findByEmail(USER_EMAIL).get();
        String password = user.getPassword();
        user.setPassword(encoderService.encode(password));
        userRepository.save(user);
    }

    /* ============= USER ============== */

    @Test
    public void getSimpleUserThenReturnDto() throws Exception {
        //when
        mockMvc.perform(get(PATH)
                .header("Authorization", JwtService.TOKEN_PREFIX + jwt))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()));
    }

    @Test
    public void updateSimpleUserThenReturnDto() throws Exception {
        SimpleUserDto dto = new SimpleUserDto(user.getId(), "TestFName", "TestLName", "45646879");
        //when
        mockMvc.perform(put(PATH)
                .header("Authorization", JwtService.TOKEN_PREFIX + jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(dto.getFirstName()));
    }

    @Test
    public void updateSimpleUserThenReturnBedRequest() throws Exception {
        SimpleUserDto dto = new SimpleUserDto(99L, "TestFName", "TestLName", "45646879");
        //when
        mockMvc.perform(put(PATH)
                .header("Authorization", JwtService.TOKEN_PREFIX + jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                //then
                .andExpect(status().isBadRequest());
    }

    /* ============ CAR ============== */

    @Test
    public void getListUserCarThenReturnList() throws Exception {
        mockMvc.perform(get(PATH + "/cars")
                .header("Authorization", JwtService.TOKEN_PREFIX + jwt))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getUserCarByIdThenReturnCar() throws Exception {
        //given
        UserCar car = userCarRepository.findAllBySimpleUserId(user.getId()).get(0);
        //when
        mockMvc.perform(get(PATH + "/car/{id}", car.getId())
                .header("Authorization", JwtService.TOKEN_PREFIX + jwt))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.releaseYear").value(car.getReleaseYear()));
    }

    @Test
    public void getUserCarByIdThenReturn404() throws Exception {
        //when
        mockMvc.perform(get(PATH + "/car/{id}", 998L)
                .header("Authorization", JwtService.TOKEN_PREFIX + jwt))
                //then
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteUserCarThenReturnOK() throws Exception {
        //given
        UserCar car = userCarRepository.findAllBySimpleUserId(user.getId()).get(0);
        //when
        mockMvc.perform(delete(PATH + "/car/{id}", car.getId())
                .header("Authorization", JwtService.TOKEN_PREFIX + jwt))
                //then
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUserCarThenReturn404() throws Exception {
        //when
        mockMvc.perform(delete(PATH + "/car/{id}", 999L)
                .header("Authorization", JwtService.TOKEN_PREFIX + jwt))
                //then
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenCreateUserCarThenReturnThisOne() throws Exception {
        UserCarDto carDto = createCar();
        MockMultipartHttpServletRequestBuilder multipart = MockMvcRequestBuilders.multipart(PATH + "/car");
        mockMvc.perform(multipart
                .file(getPart(objectMapper.writeValueAsString(carDto)))
                .file(getMultipartFile())
                .file(getMultipartFile())
                .header("Authorization", JwtService.TOKEN_PREFIX + jwt)
        )
                //then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.releaseYear").value(carDto.getReleaseYear()))
                .andExpect(jsonPath("$.images", hasSize(2)));
    }

    @Test
    public void whenUpdateUserCarThenReturnCar() throws Exception {
        UserCarDto carDto = userCarService.getByUserId(user.getId()).get(0);
        carDto.setReleaseYear(1999);
        mockMvc.perform(put(PATH + "/car")
                .header("Authorization", JwtService.TOKEN_PREFIX + jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(carDto)))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.images", hasSize(carDto.getImages().size())))
                .andExpect(jsonPath("$.releaseYear").value(carDto.getReleaseYear()));
    }

    // test objects

    private UserCarDto createCar(){
        CarModel model = carModelRepository.findAll().get(0);
        CarModelDto modelDto = carModelService.findById(model.getId());

        UserCarDto car = new UserCarDto();
        car.setCarModel(modelDto);
        car.setReleaseYear(2016);
        return car;
    }

    private MockMultipartFile getMultipartFile() throws IOException {
        String name = "ny.jpg";
        String contentType = MediaType.IMAGE_JPEG_VALUE;
        byte[] content = {11, 12, 13, 14, 15};
        return new MockMultipartFile("files", name, contentType, content);
    }

    private MockMultipartFile getPart(String json) {
        String contentType = MediaType.APPLICATION_JSON_VALUE;
        byte[] content = json.getBytes();
        return new MockMultipartFile("json", "json", contentType, content);
    }
}
