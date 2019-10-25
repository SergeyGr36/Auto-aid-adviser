package com.hillel.evo.adviser.security.service;

import com.hillel.evo.adviser.security.configuration.JwtPropertyConfiguration;
import com.hillel.evo.adviser.security.dto.LoginRequestDto;
import com.hillel.evo.adviser.security.dto.LoginResponseDto;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthenticationServiceTest {

    private static final String USER_MAIL = "test@gmail.com";
    private static final String USER_PASSWORD = "testtest123";
    private static final String TOKEN = "token";

    @Mock private AuthenticationManager authenticationManager;
    @Mock private JwtService jwtService;
    @Mock private JwtPropertyConfiguration jwtConfig;
    @Mock private Authentication authentication;

    private AuthenticationService authenticationService;
    private LoginRequestDto loginRequestDto;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        authenticationService = new AuthenticationService(jwtService, authenticationManager, jwtConfig);

        loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail(USER_MAIL);
        loginRequestDto.setPassword(USER_PASSWORD);
    }

    @Test
    public void whenAuthenticateAndResponce_thenAuthenticationIsCalled() {
        //when
        authenticationService.authenticateAndResponse(loginRequestDto);
        //then
        verify(authenticationManager).authenticate(any(Authentication.class));
    }

    @Test
    public void whenAuthenticateAndResponce_thenResponceWithBodyIsReturned() {
        //given
        when(jwtService.generateAccessToken(anyLong(), anyLong())).thenReturn(TOKEN);
        //when
        ResponseEntity<LoginResponseDto> responseEntity = authenticationService.authenticateAndResponse(loginRequestDto);
        LoginResponseDto responseDto = responseEntity.getBody();
        //then
        assertEquals(TOKEN, responseDto.getToken());
    }
}