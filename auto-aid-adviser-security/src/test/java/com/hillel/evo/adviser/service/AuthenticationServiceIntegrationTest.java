package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.SecurityAppStarter;
import com.hillel.evo.adviser.configuration.JwtPropertyConfiguration;
import com.hillel.evo.adviser.dto.LoginResponseDto;
import com.hillel.evo.adviser.dto.LoginRequestDto;
import com.hillel.evo.adviser.entity.AdviserUserDetails;
import com.hillel.evo.adviser.repository.AdviserUserDetailRepository;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.HttpStatus.OK;


@SpringBootTest(classes = SecurityAppStarter.class)
@Sql(value = {"/create-user.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class AuthenticationServiceIntegrationTest {

    private static final String EMAIL = "test@gmail.com";
    private static final String PASSWORD = "testtest123";

    private LoginRequestDto loginRequestDto;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    JwtPropertyConfiguration jwtProperties;

    @Autowired
    private AdviserUserDetailRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @BeforeEach
    public void setUp() {
        encodeTestUserPassword();
        loginRequestDto = createTestLoginRequestDto();
    }

    @Test
    public void whenValidCredentialsProvided_thenUserIsAuthenticated() {

         ResponseEntity<LoginResponseDto> response =
                 authenticationService.authenticateAndResponse(createTestLoginRequestDto());

         Assertions.assertEquals(OK, response.getStatusCode());
    }

    @Test
    public void whenBadCredentialsProvided_thenThrowsBadCredentialsException() {

        loginRequestDto.setPassword("wrongPassword000");

        assertThrows(BadCredentialsException.class,
                () -> authenticationService.authenticateAndResponse(loginRequestDto));
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
    }
}