package com.hillel.evo.adviser.controller;

import com.hillel.evo.adviser.dto.AdviserUserDetailsDto;
import com.hillel.evo.adviser.dto.LoginRequestDto;
import com.hillel.evo.adviser.dto.LoginResponseDto;
import com.hillel.evo.adviser.dto.UserRegistrationDto;
import com.hillel.evo.adviser.service.AuthenticationService;
import com.hillel.evo.adviser.service.RegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import static org.springframework.http.HttpStatus.OK;

@Validated
@RestController()
@RequestMapping("/user")
public class RegistrationController {

    private final transient RegistrationService registrationService;
    private final transient AuthenticationService authenticationService;

    public RegistrationController(RegistrationService registrationService, AuthenticationService authenticationService) {
        this.registrationService = registrationService;
        this.authenticationService = authenticationService;
    }

    /**
     * Registers a new user with provided email, password and role.
     *
     * Email string should be a well formed email.
     * Password should be 8-64 symbols long, could contain uppercase and lowercase latin letters,
     * digits 0-9, and underscores.
     * Should contain at least one digit, one uppercase and one lowercase letter.
     * Violation of these rules results in Bad Request status 400.
     *
     * If the user with provided email already exist, the returned status is Conflict 409.
     *
     * @param registrationDto
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<AdviserUserDetailsDto> registerUser(
            @RequestBody @Valid UserRegistrationDto registrationDto) {

        return ResponseEntity.status(OK).body(registrationService.registerUser(registrationDto));
    }


    /**
     * Activates a new user.
     * Returns a response with status ok, jwt token in "Authorization" header, and user dto body.
     * Jwt holds user id.
     *
     * If activation code is malformed, returns status Bad Request 400.
     * If activation code is invalid, returns status Bad Request 400.
     * @param activationCode the activation code from email.
     * @return Returns a response with status ok, jwt token in "Authorization" header, and user dto body.
     */
    @PostMapping("/activate/{activationCode}")
    public ResponseEntity<AdviserUserDetailsDto> activateUser(
            @PathVariable @Pattern(regexp = "^[a-fA-F0-9-]+$") String activationCode) {

        return registrationService.activateUser(activationCode);
    }

    /**
     * Authenticates the user with provided credentials - email and password.
     * If authentication fails, returns status 401 Unauthorized.
     * If user credentials violate required patterns, returns status Bad Request 400.
     *
     * Returns a response with jwt token in "Authorization" header, a LoginResponseDto body, and status ok.
     * Jwt holds user id.
     * @param loginRequestDTO the request, containing user credentials.
     * @return An http response with status ok and with jwt token,
     * or an error status Unauthorized 401, or an error status Bad Request 400.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> authenticateUser(
            @Valid @RequestBody final LoginRequestDto loginRequestDTO) {

        return authenticationService.authenticateAndResponse(loginRequestDTO);
    }
}
