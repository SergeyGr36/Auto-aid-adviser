package com.hillel.evo.adviser.security.service;

import com.hillel.evo.adviser.security.dto.LoginRequestDto;
import com.hillel.evo.adviser.security.dto.LoginResponseDto;
import com.hillel.evo.adviser.security.utils.JwtUtils;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;

@Service
public class AuthenticationService {

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(JwtUtils jwtUtils, AutoAidUserDetailsService userDetailsService, AuthenticationManager authenticationManager) {
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    public ResponseEntity<LoginResponseDto> authenticateAndResponse(LoginRequestDto loginRequestDTO) {

        final String userName = loginRequestDTO.getEmail();
        final String password = loginRequestDTO.getPassword();

        authenticateUser(userName, password);

        String accessToken = jwtUtils.generateAccessToken(userName);

        return ResponseEntity
                .status(OK)
                .header(AUTHORIZATION, JwtUtils.TOKEN_PREFIX + accessToken)
                .body(new LoginResponseDto(accessToken));
    }

    private void authenticateUser(final String userName, final String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userName, password);

        authenticationManager.authenticate(authenticationToken);
    }
}
