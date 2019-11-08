package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.SecurityAppStarter;
import com.hillel.evo.adviser.entity.AdviserUserDetails;
import com.hillel.evo.adviser.repository.AdviserUserDetailRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = SecurityAppStarter.class)
@Sql(value = {"/create-user.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class RegistrationServiceIntegrationTest {

    private static final String USER_EMAIL = "test@gmail.com";
    private static final String ACTIVATION_CODE = "aaa-bbb-ccc-123";

    @Autowired
    private AdviserUserDetailRepository userRepository;

    @Autowired
    private RegistrationService registrationService;

    @Test
    public void whenValidActivationCodeProvided_thenUserIsActivated() throws Exception {
        //given
        setActivationCodeClearActive();
        assertFalse(userRepository.findByEmail(USER_EMAIL).get().isActive());
        //when
        registrationService.activateUser(ACTIVATION_CODE);
        // then
        assertTrue(userRepository.findByEmail(USER_EMAIL).get().isActive());
    }

    private void setActivationCodeClearActive() {
        AdviserUserDetails user = userRepository.findByEmail(USER_EMAIL).get();
        user.setActive(false);
        user.setActivationCode(ACTIVATION_CODE);
        userRepository.save(user);
    }

}
