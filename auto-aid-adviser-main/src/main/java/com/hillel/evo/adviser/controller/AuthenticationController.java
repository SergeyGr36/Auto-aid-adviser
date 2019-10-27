package com.hillel.evo.adviser.controller;

import com.hillel.evo.adviser.dto.LoginRequestDto;
import com.hillel.evo.adviser.dto.LoginResponseDto;
import com.hillel.evo.adviser.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class AuthenticationController {

    private final transient AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(final AuthenticationService authService) {
        this.authenticationService = authService;
    }

    /**
     * Authenticates the user with provided credentials - email and password.
     * If authentication fails, returns status 401.
     *
     * Returns a response with jwt token in "Authorization" header, a LoginResponseDto body, and status ok.
     * Jwt holds user id.
     * @param loginRequestDTO the request, containing user credentials.
     * @return An http response with jwt token, or an error status 401.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> authenticateUser(
            @Valid @RequestBody final LoginRequestDto loginRequestDTO) {

        return authenticationService.authenticateAndResponse(loginRequestDTO);
    }

}
