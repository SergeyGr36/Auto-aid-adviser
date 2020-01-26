package com.hillel.evo.adviser.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillel.evo.adviser.AdviserStarter;
import com.hillel.evo.adviser.BaseTest;
import com.hillel.evo.adviser.configuration.HibernateSearchConfig;
import com.hillel.evo.adviser.dto.BusinessDto;
import com.hillel.evo.adviser.dto.ContactDto;
import com.hillel.evo.adviser.dto.FeedbackDto;
import com.hillel.evo.adviser.dto.ImageDto;
import com.hillel.evo.adviser.dto.LocationDto;
import com.hillel.evo.adviser.dto.ServiceForBusinessShortDto;
import com.hillel.evo.adviser.entity.AdviserUserDetails;
import com.hillel.evo.adviser.entity.Business;
import com.hillel.evo.adviser.entity.ServiceForBusiness;
import com.hillel.evo.adviser.repository.AdviserUserDetailRepository;
import com.hillel.evo.adviser.repository.BusinessRepository;
import com.hillel.evo.adviser.repository.FeedbackRepository;
import com.hillel.evo.adviser.repository.ServiceForBusinessRepository;
import com.hillel.evo.adviser.service.BusinessService;
import com.hillel.evo.adviser.service.EncoderService;
import com.hillel.evo.adviser.service.FeedbackService;
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
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.endsWith;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AdviserStarter.class)
@AutoConfigureMockMvc
@Sql(value = {"/clean-all.sql", "/create-user2.sql", "/create-business.sql", "/create-image.sql", "/create-feedback.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class BusinessControllerTest extends BaseTest {

    private static final String BUSINESS_EMAIL = "bvg@mail.com";
    private static final String SIMPLEUSER_EMAIL = "svg@mail.com";
    private static final String BUSINESS_EMAIL_ALIEN = "bkc@mail.com";
    private static final String PATH_BUSINESSES =  "/businesses";

    private AdviserUserDetails user;
    private String jwt;

    @Autowired
    private HibernateSearchConfig hibernateSearchConfig;

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

    @Autowired
    FeedbackService feedbackService;

    @BeforeEach
    public void setUp() throws Exception {
        encodeTestUserPassword();
        user = userRepository.findByEmail(BUSINESS_EMAIL).get();
        jwt = jwtService.generateAccessToken(user.getId());

        when(mockCloudImageService.hasDeletedFile(endsWith(".jpg"))).thenReturn(true);
        when(mockCloudImageService.hasDeletedFile(endsWith(".bad"))).thenReturn(false);
        when(mockCloudImageService.hasUploadedFile(any(), any())).thenReturn(true);
        when(mockCloudImageService.hasUploadedFileList(any(), any(List.class))).thenReturn(true);
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
        Business business = businessRepository.findAllByName("user 1 STO 2").get(0);
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
    public void createBusinessWithFiles() throws Exception {
        //given
        BusinessDto businessDto = createTestDto();
        //when
        MockMultipartHttpServletRequestBuilder multipart = MockMvcRequestBuilders.multipart(PATH_BUSINESSES);
        mockMvc.perform(multipart
                .file(getPart(objectMapper.writeValueAsString(businessDto)))
                .file(getMultipartFile())
                .file(getMultipartFile())
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
    public void addImageToBusinessReturnDto() throws Exception {
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
                .andExpect(jsonPath("$[0].originalFileName").value(multipartFile.getOriginalFilename()));
    }

    @Test
    public void deleteImageFromBusinessReturnOk() throws Exception {
        //given
        List<Business> allBusinessByName = businessRepository.findAllByName("user 1 STO 1");
        Business business = allBusinessByName.get(0);
        ImageDto imagesDto = businessService.findImagesByBusinessId(business.getId()).get(0);
        //when
        mockMvc.perform(delete(PATH_BUSINESSES+"/{businessId}/images/{imageId}", business.getId(), imagesDto.getId())
                .header("Authorization", JwtService.TOKEN_PREFIX + jwt))
                //then
                .andExpect(status().isOk());
    }

    @Test
    public void deleteImageFromBusinessReturn404() throws Exception {
        //given
        List<Business> allBusinessByName = businessRepository.findAllByName("user 1 STO 1");
        Business business = allBusinessByName.get(0);
        //when
        mockMvc.perform(delete(PATH_BUSINESSES+"/{businessId}/images/{imageId}", business.getId(), 99L)
                .header("Authorization", JwtService.TOKEN_PREFIX + jwt))
                //then
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteImageFromBusinessReturnBabRequest() throws Exception {
        //given
        List<Business> allBusinessByName = businessRepository.findAllByName("user 1 STO 1");
        Business business = allBusinessByName.get(0);
        ImageDto imagesDto = businessService.findImagesByBusinessId(business.getId())
                .stream().filter(dto -> dto.getOriginalFileName().endsWith(".bad"))
                .findFirst().get();
        //when
        mockMvc.perform(delete(PATH_BUSINESSES+"/{businessId}/images/{imageId}", business.getId(), imagesDto.getId())
                .header("Authorization", JwtService.TOKEN_PREFIX + jwt))
                //then
                .andExpect(status().isBadRequest());
    }

    /* ================ feedback =============== */

    @Test
    public void getPageFeedBackByBusiness() throws Exception {
        //given
        List<Business> allBusinessByName = businessRepository.findAllByName("user 1 STO 1");
        Business business = allBusinessByName.get(0);
        //when
        mockMvc.perform(get(PATH_BUSINESSES+"/{businessId}/feedbacks", business.getId())
                .header("Authorization", JwtService.TOKEN_PREFIX + jwt))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.totalElements").value(4));
    }

    @Test
    public void saveFeedBackReturnDto() throws Exception {
        //given
        AdviserUserDetails simpleUser = userRepository.findByEmail(SIMPLEUSER_EMAIL).get();
        String suJwt = jwtService.generateAccessToken(simpleUser.getId());
        List<Business> allBusinessByName = businessRepository.findAllByName("user 1 STO 1");
        Business business = allBusinessByName.get(0);
        FeedbackDto feedbackDto = new FeedbackDto();
        feedbackDto.setText("test");
        feedbackDto.setRating(5);
        //when
        mockMvc.perform(post(PATH_BUSINESSES+"/{businessId}/feedback", business.getId())
                .header("Authorization", JwtService.TOKEN_PREFIX + suJwt)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(feedbackDto)))
                //then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.text").value(feedbackDto.getText()))
                .andExpect(jsonPath("$.rating").value(feedbackDto.getRating()));
    }

    @Test
    public void updateFeedBackReturnDto() throws Exception {
        //given
        AdviserUserDetails simpleUser = userRepository.findByEmail(SIMPLEUSER_EMAIL).get();
        String suJwt = jwtService.generateAccessToken(simpleUser.getId());
        List<Business> allBusinessByName = businessRepository.findAllByName("user 1 STO 1");
        Business business = allBusinessByName.get(0);

        FeedbackDto feedbackDto = feedbackService.findFeedbackByBusiness(business.getId(), 1, 1)
                .get().findFirst().get();

        feedbackDto.setText("test");
        feedbackDto.setRating(5);
        //when
        mockMvc.perform(put(PATH_BUSINESSES+"/{businessId}/feedback", business.getId())
                .header("Authorization", JwtService.TOKEN_PREFIX + suJwt)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(feedbackDto)))
                //then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(feedbackDto.getId()))
                .andExpect(jsonPath("$.text").value(feedbackDto.getText()))
                .andExpect(jsonPath("$.rating").value(feedbackDto.getRating()));
    }

    /* ========================================= */

    private BusinessDto createTestDto() {
        List<ServiceForBusiness> list = serviceForBusinessRepository.findAll();

        BusinessDto dto = new BusinessDto();
        dto.setName("some name");

        LocationDto location = new LocationDto();
        location.setLatitude(60.0);
        location.setLongitude(60.0);
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
