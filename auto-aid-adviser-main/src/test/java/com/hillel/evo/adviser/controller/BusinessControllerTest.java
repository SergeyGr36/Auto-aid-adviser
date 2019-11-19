package com.hillel.evo.adviser.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillel.evo.adviser.AdviserStarter;
import com.hillel.evo.adviser.dto.BusinessDto;
import com.hillel.evo.adviser.dto.ContactDto;
import com.hillel.evo.adviser.dto.LocationDto;
import com.hillel.evo.adviser.dto.ServiceForBusinessShortDto;
import com.hillel.evo.adviser.entity.AdviserUserDetails;
import com.hillel.evo.adviser.entity.Business;
import com.hillel.evo.adviser.entity.ServiceForBusiness;
import com.hillel.evo.adviser.repository.AdviserUserDetailRepository;
import com.hillel.evo.adviser.repository.BusinessRepository;
import com.hillel.evo.adviser.repository.ServiceForBusinessRepository;
import com.hillel.evo.adviser.service.EncoderService;
import com.hillel.evo.adviser.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AdviserStarter.class)
@AutoConfigureMockMvc
@Sql(value = {"/clean-business.sql", "/clean-user.sql", "/create-user2.sql", "/create-business.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/clean-business.sql", "/clean-user.sql"},
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

    @BeforeEach
    public void setUp() {
        encodeTestUserPassword();
        user = userRepository.findByEmail(BUSINESS_EMAIL).get();
        jwt = jwtService.generateAccessToken(user.getId());
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
        Business business = businessRepository.findAllByBusinessUser_Id(user.getId()).get(0);
        BusinessDto businessDto = createTestDto();
        businessDto.setId(business.getId());
        //when
        mockMvc.perform(get(PATH_BUSINESSES+"/{id}/services", business.getId())
                .header("Authorization", JwtService.TOKEN_PREFIX + jwt))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
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
}
