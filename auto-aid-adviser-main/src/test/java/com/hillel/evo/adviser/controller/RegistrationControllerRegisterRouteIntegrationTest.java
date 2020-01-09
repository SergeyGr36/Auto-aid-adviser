package com.hillel.evo.adviser.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillel.evo.adviser.AdviserStarter;
import com.hillel.evo.adviser.BaseTest;
import com.hillel.evo.adviser.dto.UserRegistrationDto;
import com.hillel.evo.adviser.entity.AdviserUserDetails;
import com.hillel.evo.adviser.enums.RoleUser;
import com.hillel.evo.adviser.repository.AdviserUserDetailRepository;
import com.hillel.evo.adviser.service.EmailService;
import com.hillel.evo.adviser.service.EncoderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AdviserStarter.class)
@AutoConfigureMockMvc
@Sql(value = {"/clean-all.sql", "/create-user.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class RegistrationControllerRegisterRouteIntegrationTest extends BaseTest {

    private static final String REGISTER_ROUTE = "/user/register";


    private static final String EXISTING_USER_EMAIL = "test@gmail.com";
    private static final String EXISTING_USER_PASSWORD = "Testtest123";

    private static final String NEW_USER_EMAIL = "new@gmail.com";
    private static final String NEW_USER_PASSWORD = "TestTest123";


    private UserRegistrationDto registrationDto;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AdviserUserDetailRepository userRepository;

    @Autowired
    EncoderService encoderService;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    EmailService emailService;


    @BeforeEach
    public void setUp() {
        encodeTestUserPassword();
        registrationDto = createTestRegistrationDto();

        when(emailService.sendMessage(any())).thenReturn(true);
    }

    @Test
    public void whenValidCredentialsProvided_thenReturnStatusIsOk() throws Exception {

        mockMvc.perform(post(REGISTER_ROUTE)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(registrationDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void whenValidCredentialsProvided_thenNewUserIsCreated() throws Exception {

        assertFalse(userRepository.existsByEmail(NEW_USER_EMAIL));

        mockMvc.perform(post(REGISTER_ROUTE)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(registrationDto)))
                .andExpect(status().isOk());

        assertTrue(userRepository.existsByEmail(NEW_USER_EMAIL));
    }

    @Test
    public void whenUserAlreadyExists_thenReturnStatusIsConflict409() throws Exception {

        assertTrue(userRepository.existsByEmail(EXISTING_USER_EMAIL));

        registrationDto.setEmail(EXISTING_USER_EMAIL);

        mockMvc.perform(post(REGISTER_ROUTE)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(registrationDto)))
                .andExpect(status().isConflict());
    }

    @Test
    public void whenMalformedEmailProvided_thenReturnStatusIsBadRequest400() throws Exception {

        registrationDto.setEmail("as=1@lll.mm");

        mockMvc.perform(post(REGISTER_ROUTE)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(registrationDto)))
                .andExpect(status().isBadRequest());
    }

    private UserRegistrationDto createTestRegistrationDto() {
        UserRegistrationDto dto = new UserRegistrationDto();
        dto.setEmail(NEW_USER_EMAIL);
        dto.setPassword(NEW_USER_PASSWORD);
        dto.setRole(RoleUser.ROLE_USER);
        return dto;
    }

    private void encodeTestUserPassword() {
        AdviserUserDetails user = userRepository.findByEmail(EXISTING_USER_EMAIL).get();
        String password = user.getPassword();
        user.setPassword(encoderService.encode(password));
        userRepository.save(user);
    }
}
