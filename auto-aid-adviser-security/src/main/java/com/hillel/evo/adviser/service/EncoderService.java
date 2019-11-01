package com.hillel.evo.adviser.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EncoderService {

    private final PasswordEncoder encoder;

    public EncoderService(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public String encode(String password) {
        return encoder.encode(password);
    }
}
