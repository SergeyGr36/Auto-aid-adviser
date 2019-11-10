package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.dto.AdviserUserDetailsDto;
import com.hillel.evo.adviser.dto.UserRegistrationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;

@Service
public class RegistrationService {

    final private transient UserService userService;
    final private transient EncoderService encoderService;
    final private transient JwtService jwtService;
    final private transient SecurityUserDetailsService detailsService;

    public RegistrationService(UserService userService,
                               EncoderService encoderService,
                               JwtService jwtService,
                               SecurityUserDetailsService detailsService) {
        this.userService = userService;
        this.encoderService = encoderService;
        this.jwtService = jwtService;
        this.detailsService = detailsService;
    }

    /**
     * Registers a new user. Relays on the user service for implementation.
     * @param dto - registration dto
     * @return user dto of created user
     */
    public AdviserUserDetailsDto registerUser(UserRegistrationDto dto) {

        String password = dto.getPassword();
        dto.setPassword(encoderService.encode(password));

        return userService.registration(dto);
    }

    /**
     * Activates a new user. Relays on the user service for implementation.
     * Returns a response with jwt token in "Authorization" header, a user dto body, and status ok.
     * Jwt holds user id.
     * @param activationCode - registration dto
     * @return user dto of created user
     */
    public ResponseEntity<AdviserUserDetailsDto> activateUser(String activationCode) {

        AdviserUserDetailsDto userDto =  userService.activation(activationCode);

        long userId = userDto.getId();

        Authentication trustedAuthentication = detailsService.createTrustedAuthenticationWithUserId(userId);

        SecurityContextHolder.getContext().setAuthentication(trustedAuthentication);

        String accessToken = jwtService.generateAccessToken(userId);

        return ResponseEntity
                .status(OK)
                .header(AUTHORIZATION, JwtService.TOKEN_PREFIX + accessToken)
                .body(userDto);
    }
}
