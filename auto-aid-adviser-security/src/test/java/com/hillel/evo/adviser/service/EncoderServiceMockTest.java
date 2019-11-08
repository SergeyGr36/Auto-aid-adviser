package com.hillel.evo.adviser.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EncoderServiceMockTest {

    @Mock PasswordEncoder encoder;

    private EncoderService encoderService;

    @BeforeEach
    public void setUp() {
        encoderService = new EncoderService(encoder);
    }

    @Test
    public void whenEncode_thenPasswordEncoderIsCalled() {

        encoderService.encode("Password");

        Mockito.verify(encoder).encode("Password");
    }
}