package com.hillel.evo.adviser.controller;

import com.hillel.evo.adviser.BaseTest;
import com.hillel.evo.adviser.SecurityAppStarter;
import com.hillel.evo.adviser.entity.AdviserUserDetails;
import com.hillel.evo.adviser.repository.AdviserUserDetailRepository;
import com.hillel.evo.adviser.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {SecurityAppStarter.class})
@Import(TestController.class)
@AutoConfigureMockMvc
@Sql(value = {"/create-user.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class MethodSecurityIntegrationTest extends BaseTest {

    private static final String EMAIL = "test@gmail.com";
    private static final String PASSWORD = "testtest123";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AdviserUserDetailRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtService jwtService;

    private AdviserUserDetails user;

    @BeforeEach
    public void setup() {
        user = userRepository.findByEmail(EMAIL).get();
        encodeTestUserPassword();
    }

    @Test
    public void whenRequestSecuredRouteWithValidJwt_thenResponseStatusIsOk() throws Exception {

        String jwtToken = jwtService.generateAccessToken(user.getId());

        mockMvc.perform(
                get("/test/secured")
                        .contentType("application/json")
                        .header("Authorization", JwtService.TOKEN_PREFIX + jwtToken))
                .andExpect(status().isOk());

    }

    @Test
    public void whenRequestSecuredRouteWithoutJwt_thenResponseStatusIsUnauthorized() throws Exception {

        String jwtToken = jwtService.generateAccessToken(user.getId());

        mockMvc.perform(
                get("/test/secured")
                        .contentType("application/json")
                        .header("Authorization", ""))
                .andExpect(status().isUnauthorized());

    }


    private void encodeTestUserPassword() {
        String password = user.getPassword();
        user.setPassword(encoder.encode(password));
        userRepository.save(user);
    }
}
