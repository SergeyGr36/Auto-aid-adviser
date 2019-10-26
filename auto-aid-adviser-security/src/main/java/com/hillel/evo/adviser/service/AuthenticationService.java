package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.configuration.JwtPropertyConfiguration;
import com.hillel.evo.adviser.dto.LoginRequestDto;
import com.hillel.evo.adviser.dto.LoginResponseDto;

import com.hillel.evo.adviser.entity.AdviserUserDetails;
import com.hillel.evo.adviser.repository.AdviserUserDetailRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;

@Service
public class AuthenticationService {

    private final transient JwtService jwtService;
    private final transient AuthenticationManager authenticationManager;
    private final transient JwtPropertyConfiguration jwtPropertyConfiguration;
    private final transient AdviserUserDetailRepository userRepository;

    public AuthenticationService(JwtService jwtService,
                                 AuthenticationManager authenticationManager,
                                 JwtPropertyConfiguration jwtPropertyConfiguration,
                                 AdviserUserDetailRepository userRepository) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.jwtPropertyConfiguration = jwtPropertyConfiguration;
        this.userRepository = userRepository;
    }


    /**
     * Authenticates the user with provided credentials - email and password.
     * If authentication fails, throws AuthenticationException.
     *
     * Returns a response with jwt token in "Authorization" header, a LoginResponseDto body, and status ok.
     * Jwt holds user id.
     * @param loginRequestDTO the request, containing user credentials.
     * @return An http response with jwt token.
     *
     * @throws org.springframework.security.core.AuthenticationException
     */
    public ResponseEntity<LoginResponseDto> authenticateAndResponse(LoginRequestDto loginRequestDTO) {

        final String userName = loginRequestDTO.getEmail();
        final String password = loginRequestDTO.getPassword();

        authenticateUser(userName, password);

        AdviserUserDetails user = userRepository.findByEmail(userName).get();

        long userId = user.getId();

        String accessToken = jwtService.generateAccessToken(userId, jwtPropertyConfiguration.getExpirationMillis());

        return ResponseEntity
                .status(OK)
                .header(AUTHORIZATION, JwtService.TOKEN_PREFIX + accessToken)
                .body(new LoginResponseDto(accessToken));
    }

    /** Creates an untrusted (isAuthenticated = false) Authentication with user credentials,
     * and calls AthenticationManager authenticate method.
     * Throws org.springframework.security.core.AuthenticationException
     *
     * @param userName user email.
     * @param password user password.
     * @throws org.springframework.security.core.AuthenticationException
     */
    private void authenticateUser(final String userName, final String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userName, password);

        authenticationManager.authenticate(authenticationToken);
    }
}
