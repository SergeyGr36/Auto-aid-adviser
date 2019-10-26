package com.hillel.evo.adviser.controller;

import com.hillel.evo.adviser.dto.BusinessUserRegistrationDto;
import com.hillel.evo.adviser.dto.RegistrationResponseDto;
import com.hillel.evo.adviser.dto.SimpleUserRegistrationDto;
import com.hillel.evo.adviser.service.PasswordEncodingRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController("/")
public class RegistrationController {

    private final transient PasswordEncodingRegistrationService passwordEncodingRegistrationService;

    public RegistrationController(PasswordEncodingRegistrationService passwordEncodingRegistrationService) {
        this.passwordEncodingRegistrationService = passwordEncodingRegistrationService;
    }

    @PostMapping("/register/user")
    public ResponseEntity<RegistrationResponseDto> registerSimpleUser(
            @RequestBody @Valid SimpleUserRegistrationDto registrationDto) {

        return ResponseEntity.status(HttpStatus.OK).body(passwordEncodingRegistrationService.registerSimpleUser(registrationDto));
    }


    @PostMapping("/register/businessuser")
    public ResponseEntity<RegistrationResponseDto> registerBusinessUserUser(
            @RequestBody @Valid BusinessUserRegistrationDto registrationDto) {

        return ResponseEntity.status(HttpStatus.OK).body(passwordEncodingRegistrationService.registerBusinessUser(registrationDto));
    }
}
