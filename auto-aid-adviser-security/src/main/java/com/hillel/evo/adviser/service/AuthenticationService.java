package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.dto.LoginRequestDto;
import com.hillel.evo.adviser.dto.UserTokenResponseDto;

import com.hillel.evo.adviser.entity.AdviserUserDetails;
import com.hillel.evo.adviser.repository.AdviserUserDetailRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;

@Service
public class AuthenticationService {

    private final transient JwtService jwtService;
    private final transient AuthenticationManager authenticationManager;

    private final transient AdviserUserDetailRepository userRepository;
    private final transient SecurityUserDetailsService detailsService;

    public AuthenticationService(JwtService jwtService,
                                 AuthenticationManager authenticationManager,
                                 AdviserUserDetailRepository userRepository,
                                 SecurityUserDetailsService detailsService) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.detailsService = detailsService;
    }


    /**
     * Authenticates the user with provided credentials - email and password.
     * If authentication fails, throws AuthenticationException.
     *
     * Returns a response with jwt token in "Authorization" header, a UserTokenResponseDto body, and status ok.
     * Jwt holds user id.
     * @param loginRequestDTO the request, containing user credentials.
     * @return An http response with jwt token.
     *
     * @throws org.springframework.security.core.AuthenticationException
     */
    public ResponseEntity<UserTokenResponseDto> authenticateAndResponse(LoginRequestDto loginRequestDTO) {

        final String userName = loginRequestDTO.getEmail();
        final String password = loginRequestDTO.getPassword();

        authenticateUserWithCredentials(userName, password);

        AdviserUserDetails user = userRepository.findByEmail(userName).get();

        long userId = user.getId();

        String accessToken = jwtService.generateAccessToken(userId);

        return ResponseEntity
                .status(OK)
                .header(AUTHORIZATION, JwtService.TOKEN_PREFIX + accessToken)
                .header(ACCESS_CONTROL_ALLOW_HEADERS, AUTHORIZATION)
                .body(createDto(user, accessToken));
    }

    /** Creates an untrusted (isAuthenticated = false) Authentication with user credentials,
     * and relies on AthenticationManager authenticate method to authenticate user.
     * Throws org.springframework.security.core.AuthenticationException
     *
     * @param userName user email.
     * @param password user password.
     * @throws org.springframework.security.core.AuthenticationException
     */
    private Authentication authenticateUserWithCredentials(final String userName, final String password) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userName, password);

        return authenticationManager.authenticate(authenticationToken);
    }

    private UserTokenResponseDto createDto(AdviserUserDetails user, String token) {
        UserTokenResponseDto dto = new UserTokenResponseDto(
                user.getId(),
                user.getEmail(),
                user.getRole(),
                token
        );
        return dto;
    }
}