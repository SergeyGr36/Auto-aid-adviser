package com.hillel.evo.adviser.security.controller;

import com.hillel.evo.adviser.security.dto.LoginRequestDto;
import com.hillel.evo.adviser.security.dto.LoginResponseDto;
import com.hillel.evo.adviser.security.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/")
public class AuthenticationController {

    private final transient AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(final AuthenticationService authService) {
        this.authenticationService = authService;
    }

    /**
     *
     * @param authRequest
     * @param response
     * @return token user
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> authenticateUser(@Valid @RequestBody final LoginRequestDto loginRequestDTO) {
        return authenticationService.authenticateAndResponse(loginRequestDTO);
    }

}
