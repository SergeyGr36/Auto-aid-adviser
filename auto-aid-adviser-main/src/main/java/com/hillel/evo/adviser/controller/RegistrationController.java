package com.hillel.evo.adviser.controller;

import com.hillel.evo.adviser.dto.AdviserUserDetailsDto;
import com.hillel.evo.adviser.dto.UserRegistrationDto;
import com.hillel.evo.adviser.service.RegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PostMapping("user/register")
    public ResponseEntity<AdviserUserDetailsDto> registerUser(
            @RequestBody @Valid UserRegistrationDto registrationDto) {

        return ResponseEntity.status(OK).body(registrationService.registerUser(registrationDto));
    }


    @PostMapping("user/activate/{activationCode}")
    public ResponseEntity<AdviserUserDetailsDto> activateUser(
            @PathVariable String activationCode) {

        return registrationService.activateUser(activationCode);
    }
}
