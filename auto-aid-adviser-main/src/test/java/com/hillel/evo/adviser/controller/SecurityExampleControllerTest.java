package com.hillel.evo.adviser.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillel.evo.adviser.AdviserStarter;
import com.hillel.evo.adviser.BaseTest;
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
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AdviserStarter.class)
@AutoConfigureMockMvc
@Sql(value = {"/create-user.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class SecurityExampleControllerTest extends BaseTest {

    private static final String SECURED_ROUTE = "/example/secured";
    private static final String UNSECURED_ROUTE = "/example/unsecured";

    private static final String EMAIL = "test@gmail.com";
    private static final String BUSINESS_EMAIL = "business@gmail.com";

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
    public void whenRequestSecuredRouteWithValidJwt_thenGetGreetingWithProperUserId() throws Exception {

        String jwtToken = jwtService.generateAccessToken(user.getId());

         MvcResult result = mockMvc.perform(
                 get(SECURED_ROUTE)
                         .header("Authorization", JwtService.TOKEN_PREFIX + jwtToken))
                 .andExpect(status().isOk())
                 .andReturn();

        String greeting = result.getResponse().getContentAsString();

        assertEquals("Hello from Secured controller, your ID is: " + user.getId(), greeting);
    }



    @Test
    public void whenRequestSecuredRouteWithValidJwtButWrongRole_thenResponseStatusIsForbidden403() throws Exception {

        AdviserUserDetails businessUser = userRepository.findByEmail(BUSINESS_EMAIL).get();

        String jwtToken = jwtService.generateAccessToken(businessUser.getId());

        mockMvc.perform(
                get(SECURED_ROUTE)
                        .header("Authorization", JwtService.TOKEN_PREFIX + jwtToken))
                .andExpect(status().isForbidden());
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