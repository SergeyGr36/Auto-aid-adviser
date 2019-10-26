package com.hillel.evo.adviser.security.controller;

import com.hillel.evo.adviser.dto.BusinessUserRegistrationDto;
import com.hillel.evo.adviser.dto.SimpleUserRegistrationDto;
import com.hillel.evo.adviser.security.dto.RegistrationResponseDto;
import com.hillel.evo.adviser.security.service.RegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController("/")
public class RegistrationController {

    private final transient RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/register/user")
    public ResponseEntity<RegistrationResponseDto> registerSimpleUser(
            @RequestBody @Valid SimpleUserRegistrationDto registrationDto) {

        return ResponseEntity.status(HttpStatus.OK).body(registrationService.registerSimpleUser(registrationDto));
    }


    @PostMapping("/register/businessuser")
    public ResponseEntity<RegistrationResponseDto> registerBusinessUserUser(
            @RequestBody @Valid BusinessUserRegistrationDto registrationDto) {

        return ResponseEntity.status(HttpStatus.OK).body(registrationService.registerBusinessUser(registrationDto));
    }
}
