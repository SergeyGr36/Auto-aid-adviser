package com.hillel.evo.adviser.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillel.evo.adviser.AdviserStarter;
import com.hillel.evo.adviser.dto.BusinessDto;
import com.hillel.evo.adviser.dto.ContactDto;
import com.hillel.evo.adviser.dto.ImageDto;
import com.hillel.evo.adviser.dto.LocationDto;
import com.hillel.evo.adviser.dto.ServiceForBusinessShortDto;
import com.hillel.evo.adviser.entity.AdviserUserDetails;
import com.hillel.evo.adviser.entity.Business;
import com.hillel.evo.adviser.entity.Image;
import com.hillel.evo.adviser.entity.ServiceForBusiness;
import com.hillel.evo.adviser.repository.AdviserUserDetailRepository;
import com.hillel.evo.adviser.repository.BusinessRepository;
import com.hillel.evo.adviser.repository.ServiceForBusinessRepository;
import com.hillel.evo.adviser.service.BusinessService;
import com.hillel.evo.adviser.service.EncoderService;
import com.hillel.evo.adviser.service.JwtService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.endsWith;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AdviserStarter.class)
@AutoConfigureMockMvc
@Sql(value = {"/create-user2.sql", "/create-business.sql", "/create-image.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/clean-image.sql", "/clean-business.sql", "/clean-user.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class BusinessControllerTest {

    private static final String BUSINESS_EMAIL = "bvg@mail.com";
    private static final String BUSINESS_EMAIL_ALIEN = "bkc@mail.com";
    private static final String PATH_BUSINESSES =  "/businesses";

    private AdviserUserDetails user;
    private String jwt;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AdviserUserDetailRepository userRepository;

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private ServiceForBusinessRepository serviceForBusinessRepository;

    @Autowired
    EncoderService encoderService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    JwtService jwtService;

    @MockBean
    CloudImageService mockCloudImageService;

    @Autowired
    BusinessService businessService;

    @BeforeEach
    public void setUp() throws Exception {
        encodeTestUserPassword();
        user = userRepository.findByEmail(BUSINESS_EMAIL).get();
        jwt = jwtService.generateAccessToken(user.getId());

        when(mockCloudImageService.deleteFile(endsWith(".jpg"))).thenReturn(true);
        when(mockCloudImageService.deleteFile(endsWith(".bad"))).thenReturn(false);
        when(mockCloudImageService.uploadFile(any(), any())).thenReturn(true);
        when(mockCloudImageService.generatePresignedURL(any())).thenReturn(Optional.of(new URL("http", "localhost", "somefile")));
    }

    private void encodeTestUserPassword() {
        AdviserUserDetails user = userRepository.findByEmail(BUSINESS_EMAIL).get();
        String password = user.getPassword();
        user.setPassword(encoderService.encode(password));
        userRepository.save(user);
    }

    @Test
    public void deleteBusiness_ReturnOk() throws Exception {
        //given
        Business business = businessRepository.findAllByBusinessUserId(user.getId()).get(0);
        //when
        mockMvc.perform(delete(PATH_BUSINESSES+"/{id}", business.getId())
                .header("Authorization", JwtService.TOKEN_PREFIX + jwt))
                //then
                .andExpect(status().isOk());
    }

    @Test
    public void deleteAlienBusiness_Return404() throws Exception {
        //given
        AdviserUserDetails userAlien = userRepository.findByEmail(BUSINESS_EMAIL_ALIEN).get();
        Business businessAlien = businessRepository.findAllByBusinessUserId(userAlien.getId()).get(0);
        //when
        mockMvc.perform(delete(PATH_BUSINESSES+"/{id}", businessAlien.getId())
                .header("Authorization", JwtService.TOKEN_PREFIX + jwt))
                //then
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteMissingBusiness_Return404() throws Exception {
        mockMvc.perform(delete(PATH_BUSINESSES+"/{id}", 999)
                .header("Authorization", JwtService.TOKEN_PREFIX + jwt))
                //then
                .andExpect(status().isNotFound());
    }

    @Test
    public void getBusiness() throws Exception {
        mockMvc.perform(get(PATH_BUSINESSES)
                .header("Authorization", JwtService.TOKEN_PREFIX + jwt))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void findBusinessById() throws Exception {
        //given
        Business business = businessRepository.findAllByBusinessUserId(user.getId()).get(0);
        //when
        mockMvc.perform(get(PATH_BUSINESSES+"/{id}", business.getId())
                .header("Authorization", JwtService.TOKEN_PREFIX + jwt))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(business.getName()));
    }

    @Test
    public void findAlienBusinessById() throws Exception {
        //given
        AdviserUserDetails userAlien = userRepository.findByEmail(BUSINESS_EMAIL_ALIEN).get();
        Business businessAlien = businessRepository.findAllByBusinessUserId(userAlien.getId()).get(0);
        //when
        mockMvc.perform(get(PATH_BUSINESSES+"/{id}", businessAlien.getId())
                .header("Authorization", JwtService.TOKEN_PREFIX + jwt))
                //then
                .andExpect(status().isNotFound());
    }

    @Test
    public void createBusiness() throws Exception {
        //given
        BusinessDto businessDto = createTestDto();
        //when
        mockMvc.perform(post(PATH_BUSINESSES)
                .header("Authorization", JwtService.TOKEN_PREFIX + jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(businessDto)))
                //then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(businessDto.getName()));
    }

    @Test
    public void createBusinessWithFile() throws Exception {
        //given
        BusinessDto businessDto = createTestDto();
        //when
        mockMvc.perform(MockMvcRequestBuilders.multipart(PATH_BUSINESSES)
                    .file(getMultipartFile())
                    .file(getPart(objectMapper.writeValueAsString(businessDto)))
                .header("Authorization", JwtService.TOKEN_PREFIX + jwt)
                )
                //then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(businessDto.getName()));
    }

    @Test
    public void updateBusiness() throws Exception {
        //given
        Business business = businessRepository.findAllByBusinessUserId(user.getId()).get(0);
        BusinessDto businessDto = createTestDto();
        businessDto.setId(business.getId());
        //when
        mockMvc.perform(put(PATH_BUSINESSES)
                .header("Authorization", JwtService.TOKEN_PREFIX + jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(businessDto)))
                //then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(business.getId()))
                .andExpect(jsonPath("$.name").value(businessDto.getName()));
    }

    @Test
    public void findServiceByBusinessId() throws Exception {
        //given
        Business business = businessRepository.findAllByBusinessUserId(user.getId()).get(0);
        BusinessDto businessDto = createTestDto();
        businessDto.setId(business.getId());
        //when
        mockMvc.perform(get(PATH_BUSINESSES+"/{id}/services", business.getId())
                .header("Authorization", JwtService.TOKEN_PREFIX + jwt))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void findImageByBusinessReturnList() throws Exception {
        //given
        List<Business> allBusinessByName = businessRepository.findAllByName("user 1 STO 1");
        Business business = allBusinessByName.get(0);
        //when
        mockMvc.perform(get(PATH_BUSINESSES+"/{id}/images", business.getId())
                .header("Authorization", JwtService.TOKEN_PREFIX + jwt))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].originalFileName").isString());
    }

    @Test
    public void addImageFromBusinessReturnDto() throws Exception {
        //given
        List<Business> allBusinessByName = businessRepository.findAllByName("user 1 STO 1");
        Business business = allBusinessByName.get(0);
        MockMultipartFile multipartFile = getMultipartFile();
        //when
        mockMvc.perform(MockMvcRequestBuilders.multipart(PATH_BUSINESSES+"/{id}/images", business.getId())
                .file(multipartFile)
                .header("Authorization", JwtService.TOKEN_PREFIX + jwt))
                //then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.originalFileName").value(multipartFile.getOriginalFilename()));
    }

    @Test
    public void deleteImageFromBusinessReturnOk() throws Exception {
        //given
        List<Business> allBusinessByName = businessRepository.findAllByName("user 1 STO 1");
        Business business = allBusinessByName.get(0);
        ImageDto imagesDto = businessService.findImagesByBusinessId(business.getId()).get(0);
        //when
        mockMvc.perform(delete(PATH_BUSINESSES+"/images")
                .header("Authorization", JwtService.TOKEN_PREFIX + jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(imagesDto)))
                //then
                .andExpect(status().isOk());
    }

    @Test
    public void deleteImageFromBusinessReturnBabRequest() throws Exception {
        //given
        List<Business> allBusinessByName = businessRepository.findAllByName("user 1 STO 1");
        Business business = allBusinessByName.get(0);
        ImageDto imagesDto = businessService.findImagesByBusinessId(business.getId()).get(0);
        imagesDto.setKeyFileName("1/1/qwerty.bad");
        //when
        mockMvc.perform(delete(PATH_BUSINESSES+"/images")
                .header("Authorization", JwtService.TOKEN_PREFIX + jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(imagesDto)))
                //then
                .andExpect(status().isBadRequest());
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

        return dto;
    }

/*
    private MockMultipartFile getMultipartFile() throws IOException {
        String name = "ny.jpg";
        Path path = Paths.get("C:/Temp/" + name);
        String contentType = MediaType.IMAGE_JPEG_VALUE;
        byte[] content = Files.readAllBytes(path);
        return new MockMultipartFile("file", name, contentType, content);
    }
*/

    private MockMultipartFile getMultipartFile() throws IOException {
        String name = "ny.jpg";
        String contentType = MediaType.IMAGE_JPEG_VALUE;
        byte[] content = {11, 12, 13, 14, 15};
        return new MockMultipartFile("file", name, contentType, content);
    }

    private MockMultipartFile getPart(String json) {
        String contentType = MediaType.APPLICATION_JSON_VALUE;
        byte[] content = json.getBytes();
        return new MockMultipartFile("json", "json", contentType, content);
    }

}
