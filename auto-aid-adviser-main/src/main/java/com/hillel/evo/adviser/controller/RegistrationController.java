package com.hillel.evo.adviser.controller;

import com.hillel.evo.adviser.dto.ActivationResponseDto;
import com.hillel.evo.adviser.dto.BusinessUserRegistrationDto;
import com.hillel.evo.adviser.dto.RegistrationResponseDto;
import com.hillel.evo.adviser.dto.SimpleUserRegistrationDto;
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
    public ResponseEntity<RegistrationResponseDto> registerUser(
            @RequestBody @Valid SimpleUserRegistrationDto registrationDto) {

        return ResponseEntity.status(OK).body(registrationService.registerUser(registrationDto));
    }


    @PostMapping("user/activate/{activationCode}")
    public ResponseEntity<ActivationResponseDto> activateUser(
            @PathVariable String activationCode) {

        return registrationService.activateUser(activationCode);
    }
}
