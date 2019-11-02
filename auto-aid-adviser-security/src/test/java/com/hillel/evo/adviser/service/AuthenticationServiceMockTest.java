package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.dto.LoginRequestDto;
import com.hillel.evo.adviser.dto.LoginResponseDto;
import com.hillel.evo.adviser.entity.AdviserUserDetails;
import com.hillel.evo.adviser.repository.AdviserUserDetailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthenticationServiceMockTest {

    private static final String USER_MAIL = "test@gmail.com";
    private static final String USER_PASSWORD = "testtest123";
    private static final String TOKEN = "token";
    private static final Long USER_ID = 100L;

    @Mock private AuthenticationManager authenticationManager;
    @Mock private JwtService jwtService;
    @Mock private AdviserUserDetailRepository userRepository;
    @Mock private AdviserUserDetails user;
    @Mock private SecurityUserDetailsService detailsService;

    private AuthenticationService authenticationService;
    private LoginRequestDto loginRequestDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        authenticationService = new AuthenticationService(jwtService, authenticationManager,
                userRepository, detailsService);

        loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail(USER_MAIL);
        loginRequestDto.setPassword(USER_PASSWORD);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(user.getId()).thenReturn(USER_ID);
    }

    @Test
    public void whenAuthenticateAndResponce_thenAuthenticationIsCalled() {
        //when
        authenticationService.authenticateAndResponse(loginRequestDto);
        //then
        verify(authenticationManager).authenticate(any(Authentication.class));
    }

    @Test
    public void whenAuthenticateAndResponce_thenResponseWithBodyIsReturned() {
        //given
        when(jwtService.generateAccessToken(anyLong())).thenReturn(TOKEN);
        //when
        ResponseEntity<LoginResponseDto> responseEntity = authenticationService.authenticateAndResponse(loginRequestDto);
        LoginResponseDto responseDto = responseEntity.getBody();
        //then
        assertEquals(TOKEN, responseDto.getToken());
    }
}