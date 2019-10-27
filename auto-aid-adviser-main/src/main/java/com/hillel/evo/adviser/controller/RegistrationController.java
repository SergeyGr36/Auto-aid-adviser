package com.hillel.evo.adviser.controller;

import com.hillel.evo.adviser.dto.BusinessUserRegistrationDto;
import com.hillel.evo.adviser.dto.RegistrationResponseDto;
import com.hillel.evo.adviser.dto.SimpleUserRegistrationDto;
import com.hillel.evo.adviser.service.RegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@RestController("/")
public class RegistrationController {

    private final transient RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/register/user")
    public ResponseEntity<RegistrationResponseDto> registerSimpleUser(
            @RequestBody @Valid SimpleUserRegistrationDto registrationDto) {

        return ResponseEntity.status(OK).body(registrationService.registerSimpleUser(registrationDto));
    }


    @PostMapping("/register/businessuser")
    public ResponseEntity<RegistrationResponseDto> registerBusinessUserUser(
            @RequestBody @Valid BusinessUserRegistrationDto registrationDto) {

        return ResponseEntity.status(OK).body(registrationService.registerBusinessUser(registrationDto));
    }
}
