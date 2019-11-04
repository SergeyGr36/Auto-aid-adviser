package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.dto.UserRegistrationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceMockTest {

    @Mock EncoderService encoderService;
    @Mock UserService userService;
    @Mock JwtService jwtService;
    @Mock SecurityUserDetailsService detailsService;
    @Mock UserRegistrationDto registrationDto;

    private RegistrationService registrationService;

    @BeforeEach
    public void setup() {
        registrationService = new RegistrationService(userService, encoderService, jwtService, detailsService);
    }

    @Test
    void WhenRegisterUser_thenPasswordIsEncoded() {

        when(registrationDto.getPassword()).thenReturn("Password");

        registrationService.registerUser(registrationDto);

        verify(encoderService).encode("Password");
    }

    @Test
    void WhenRegisterUser_thenUserServiceIsCalled() {

        when(registrationDto.getPassword()).thenReturn("Password");

        registrationService.registerUser(registrationDto);

        verify(userService).registration(registrationDto);
    }

}