package com.hillel.evo.adviser.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillel.evo.adviser.AdviserStarter;
import com.hillel.evo.adviser.entity.AdviserUserDetails;
import com.hillel.evo.adviser.repository.AdviserUserDetailRepository;
import com.hillel.evo.adviser.service.EncoderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AdviserStarter.class)
@AutoConfigureMockMvc
@Sql(value = {"/create-user.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ActivateRouteIntegrationTest {

    private static final String ACTIVATE_ROUTE = "/user/activate";

    private static final String USER_EMAIL = "test@gmail.com";
    private static final String USER_PASSWORD = "testtest123";

    private static final String ACTIVATION_CODE = "aaa-bbb-ccc-123";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AdviserUserDetailRepository userRepository;

    @Autowired
    EncoderService encoderService;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        setActivationCodeClearActive();
    }

    @Test
    public void whenValidActivationCodeProvided_thenReturnStatusIsOk() throws Exception {

        mockMvc.perform(
                post(ACTIVATE_ROUTE + "/" + ACTIVATION_CODE))
                .andExpect(status().isOk());
    }

    @Test
    public void whenValidActivationCodeProvided_thenUserIsActivated() throws Exception {

        assertFalse(userRepository.findByEmail(USER_EMAIL).get().isActive());

        mockMvc.perform(
                post(ACTIVATE_ROUTE + "/" + ACTIVATION_CODE))
                .andExpect(status().isOk());

        assertTrue(userRepository.findByEmail(USER_EMAIL).get().isActive());
    }

    @Test
    public void whenWrongActivationCodeProvided_thenReturnStatusIsNotFound404() throws Exception {

        mockMvc.perform(
                post(ACTIVATE_ROUTE + "/" + "111-222"))
                .andExpect(status().isNotFound());
    }

    private void setActivationCodeClearActive() {
        AdviserUserDetails user = userRepository.findByEmail(USER_EMAIL).get();
        user.setActive(false);
        user.setActivationCode(ACTIVATION_CODE);
        userRepository.save(user);
    }
}
