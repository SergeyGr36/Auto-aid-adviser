package com.hillel.evo.adviser.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillel.evo.adviser.AdviserStarter;
import com.hillel.evo.adviser.dto.LoginRequestDto;
import com.hillel.evo.adviser.entity.AdviserUserDetails;
import com.hillel.evo.adviser.repository.AdviserUserDetailRepository;
import com.hillel.evo.adviser.service.EncoderService;
import com.hillel.evo.adviser.service.JwtService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AdviserStarter.class)
@AutoConfigureMockMvc
@Sql(value = {"/create-user.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class LoginRouteIntegrationTest {

    private static final String LOGIN_ROUTE = "/user/login";

    private static final String EMAIL = "test@gmail.com";
    private static final String PASSWORD = "testtest123";

    private LoginRequestDto loginRequestDto;

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
        loginRequestDto = createTestLoginRequestDto();
    }

    @Test
    public void whenValidCredentialsProvided_thenReturnStatusIsOk() throws Exception {

                mockMvc.perform(post(LOGIN_ROUTE)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(loginRequestDto)))
                        .andExpect(status().isOk());
    }

    @Test
    public void whenValidCredentialsProvided_thenValidTokenIsReturned() throws Exception {

        String jwtToken = mockMvc.perform(post(LOGIN_ROUTE)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginRequestDto)))
                .andReturn()
                .getResponse()
                .getHeader("Authorization")
                .replace("Bearer ","");

        Assertions.assertTrue(jwtService.isTokenValid(jwtToken));
    }


    @Test
    public void whenWrongPasswordProvided_thenReturnStatusIsUnauthorized() throws Exception {

        loginRequestDto.setPassword("WrongPassword555");

        mockMvc.perform(post(LOGIN_ROUTE)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginRequestDto)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenWrongUsernameProvided_thenReturnStatusIsUnauthorized() throws Exception {

        loginRequestDto.setEmail("wrong@test.com");

        mockMvc.perform(post(LOGIN_ROUTE)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginRequestDto)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenAccountNotActivated_thenReturnStatusIsUnauthorized() throws Exception {

        AdviserUserDetails user = userRepository.findByEmail(EMAIL).get();
        user.setActive(false);
        userRepository.save(user);

        mockMvc.perform(post(LOGIN_ROUTE)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginRequestDto)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenMalformedEmail_thenReturnStatusIsBadRequest() throws Exception {

        loginRequestDto.setEmail("?1=1@mail.ua");

        mockMvc.perform(post(LOGIN_ROUTE)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginRequestDto)))
                .andExpect(status().isBadRequest());
    }


    private LoginRequestDto createTestLoginRequestDto() {
        LoginRequestDto dto = new LoginRequestDto();
        dto.setEmail(EMAIL);
        dto.setPassword(PASSWORD);
        return dto;
    }

    private void encodeTestUserPassword() {
        AdviserUserDetails user = userRepository.findByEmail(EMAIL).get();
        String password = user.getPassword();
        user.setPassword(encoderService.encode(password));
        userRepository.save(user);
    }
}