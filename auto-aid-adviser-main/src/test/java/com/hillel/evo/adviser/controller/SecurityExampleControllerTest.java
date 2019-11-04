package com.hillel.evo.adviser.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillel.evo.adviser.AdviserStarter;
import com.hillel.evo.adviser.dto.UserRegistrationDto;
import com.hillel.evo.adviser.entity.AdviserUserDetails;
import com.hillel.evo.adviser.repository.AdviserUserDetailRepository;
import com.hillel.evo.adviser.service.EncoderService;
import com.hillel.evo.adviser.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AdviserStarter.class)
@AutoConfigureMockMvc
@Sql(value = {"/create-user.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class SecurityExampleControllerTest {

    private static final String SECURED_ROUTE = "/example/secured";
    private static final String UNSECURED_ROUTE = "/example/unsecured";

    private static final String EMAIL = "test@gmail.com";
    private static final String PASSWORD = "testtest123";

    private AdviserUserDetails user;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AdviserUserDetailRepository userRepository;

    @Autowired
    EncoderService encoderService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    JwtService jwtService;

    @BeforeEach
    public void setUp() {
        encodeTestUserPassword();
        user = userRepository.findByEmail(EMAIL).get();
    }

    @Test
    public void whenRequestSecuredRouteWithValidJwt_thenResponseStatusIsOk() throws Exception {

        String jwtToken = jwtService.generateAccessToken(user.getId());

        mockMvc.perform(
                get(SECURED_ROUTE)
                        .header("Authorization", JwtService.TOKEN_PREFIX + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    public void whenRequestSecuredRouteWithoutJwt_thenResponseStatusIsUnauthorized() throws Exception {

        mockMvc.perform(
                get(SECURED_ROUTE)
                        .header("Authorization", JwtService.TOKEN_PREFIX + "wrongToken"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenRequestSecuredRouteWithExpiredJwt_thenResponseStatusIsUnauthorized() throws Exception {

        String jwt = jwtService.generateAccessToken(user.getId(), 1);
        Thread.sleep(5);

        mockMvc.perform(
                get(SECURED_ROUTE)
                        .header("Authorization", JwtService.TOKEN_PREFIX + jwt))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenRequestUnSecuredRouteWithoutJwt_thenResponseStatusIsOk() throws Exception {

        String jwtToken = jwtService.generateAccessToken(user.getId());

        mockMvc.perform(
                get(UNSECURED_ROUTE))
                .andExpect(status().isOk());
    }


    private void encodeTestUserPassword() {
        AdviserUserDetails user = userRepository.findByEmail(EMAIL).get();
        String password = user.getPassword();
        user.setPassword(encoderService.encode(password));
        userRepository.save(user);
    }
}