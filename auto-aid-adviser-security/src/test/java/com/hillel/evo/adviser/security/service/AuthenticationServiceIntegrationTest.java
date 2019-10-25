package com.hillel.evo.adviser.security.service;

import com.hillel.evo.adviser.security.configuration.JwtPropertyConfiguration;
import com.hillel.evo.adviser.security.dto.LoginRequestDto;
import com.hillel.evo.adviser.security.dto.LoginResponseDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static org.springframework.http.HttpStatus.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Sql(value = {"/create-user.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class AuthenticationServiceIntegrationTest {

    private static final String EMAIL = "test@gmail.com";
    private static final String PASSWORD = "testtest123";

    @Autowired
    ApplicationContext context;

//    @Autowired
//    private JwtService jwtService;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private JwtPropertyConfiguration jwtPropertyConfiguration;

    @Autowired
    private AuthenticationService authenticationService;

    @Test
    public void whenValidCredentialsProvided_thenUserIsAuthenticated() {

        Arrays.stream(context.getBeanDefinitionNames()).forEach(name -> System.out.println(name));

    Assertions.assertNotNull(authenticationService);

         ResponseEntity<LoginResponseDto> response =
                 authenticationService.authenticateAndResponse(createTestLoginRequestDto());

         Assertions.assertEquals(OK, response.getStatusCode());

    }

    private LoginRequestDto createTestLoginRequestDto() {
        LoginRequestDto dto = new LoginRequestDto();
        dto.setEmail(EMAIL);
        dto.setPassword(PASSWORD);
        return dto;
    }
}