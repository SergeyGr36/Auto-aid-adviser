package com.hillel.evo.adviser.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillel.evo.adviser.AdviserStarter;
import com.hillel.evo.adviser.dto.identification.CarBrandDto;
import com.hillel.evo.adviser.dto.identification.FuelTypeDto;
import com.hillel.evo.adviser.dto.identification.MotorTypeDto;
import com.hillel.evo.adviser.dto.identification.TypeCarDto;
import com.hillel.evo.adviser.dto.UserCarDto;
import com.hillel.evo.adviser.service.SecurityUserDetails;
import com.hillel.evo.adviser.service.UserCarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AdviserStarter.class)
@AutoConfigureMockMvc
//todo удалить скрипти та .yml з тестів
public class UserProfileControllerTest {

    private static final String PATH = "/api/user/profile";

    private UserCarDto givenCar;
    private UserCarDto returnedCar;
    @Mock
    private UserCarService service;
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private UserProfileController controller;
    @Mock
    Authentication authentication;
    @Mock
    SecurityUserDetails securityUserDetails;
    private Long simpleUserId;
    @BeforeEach
    public void setUp() throws Exception {

        returnedCar = creatCar();
        returnedCar.setId(1L);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        SecurityContextHolderAwareRequestFilter authInjector = new SecurityContextHolderAwareRequestFilter();
        authInjector.afterPropertiesSet();
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).addFilters(authInjector).build();
        when(authentication.getPrincipal()).thenReturn(securityUserDetails);
        when(securityUserDetails.getUserId()).thenReturn(1L);
        simpleUserId = securityUserDetails.getUserId();
    }

    @Test
    public void whenCreateUserCarThenReturnThisOne() throws Exception {
        givenCar=creatCar();
        when(service.createUserCar(givenCar, simpleUserId)).thenReturn(returnedCar);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(PATH + "/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(givenCar));
        mockMvc.perform(builder)
                .andDo((ResultHandler) service.createUserCar(givenCar, simpleUserId))
                .andExpect(status().isOk())
                .andReturn();
//        ArgumentCaptor<UserCarDto> dtoCarCaptor = ArgumentCaptor.forClass(UserCarDto.class);
//        ArgumentCaptor<Long> userIdCaptor = ArgumentCaptor.forClass(Long.class);
//        verify(service, times(1)).createUserCar(dtoCarCaptor.capture(), userIdCaptor.capture());
    }

    private byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
    private UserCarDto creatCar(){
        givenCar = new UserCarDto();
        givenCar.setFuelType(new FuelTypeDto());
        givenCar.setMotorType(new MotorTypeDto());
        givenCar.setTypeCar(new TypeCarDto());
        givenCar.setReleaseYear(1956);
        givenCar.setBrand(new CarBrandDto(1L, "Audi"));
        return givenCar;
    }
}
