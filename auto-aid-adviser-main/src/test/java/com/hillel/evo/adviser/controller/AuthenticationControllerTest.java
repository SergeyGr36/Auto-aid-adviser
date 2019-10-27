package com.hillel.evo.adviser.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillel.evo.adviser.SecurityAppStarter;
import com.hillel.evo.adviser.configuration.SecurityConfiguration;
import com.hillel.evo.adviser.dto.LoginRequestDto;
import com.hillel.evo.adviser.entity.AdviserUserDetails;
import com.hillel.evo.adviser.repository.AdviserUserDetailRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {SecurityAppStarter.class, SecurityConfiguration.class})

@Sql(value = {"/create-user.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class AuthenticationControllerTest {

    private static final String EMAIL = "test@gmail.com";
    private static final String PASSWORD = "testtest123";

    private LoginRequestDto loginRequestDto;

    private MockMvc mockMvc;

    @Autowired
    private AdviserUserDetailRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp() {
        encodeTestUserPassword();
        loginRequestDto = createTestLoginRequestDto();

        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    public void whenValidCredentialsProvided_thenUserIsAuthenticated() throws Exception {

                mockMvc.perform(post("/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(loginRequestDto)))
                        .andExpect(status().isOk());
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
        user.setPassword(encoder.encode(password));
        userRepository.save(user);
        List<AdviserUserDetails> users = userRepository.findAll();
        users.stream().forEach(usr -> System.out.println(user.toString()));
        Assertions.assertEquals(1, users.size());
    }

}