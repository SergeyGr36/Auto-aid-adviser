package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.dto.ActivationResponseDto;
import com.hillel.evo.adviser.dto.RegistrationResponseDto;
import com.hillel.evo.adviser.dto.SimpleUserRegistrationDto;
import com.hillel.evo.adviser.dto.UserRegistrationDto;
import com.hillel.evo.adviser.entity.AdviserUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;

@Service
public class RegistrationService {

    final private transient UserService userService;
    final private transient PasswordEncoder encoder;
    final private transient JwtService jwtService;
    final private transient SecurityUserDetailsService detailsService;

    public RegistrationService(UserService userService,
                               PasswordEncoder encoder,
                               JwtService jwtService,
                               SecurityUserDetailsService detailsService) {
        this.userService = userService;
        this.encoder = encoder;
        this.jwtService = jwtService;
        this.detailsService = detailsService;
    }

    public RegistrationResponseDto registerUser(SimpleUserRegistrationDto dto) {

        String password = dto.getPassword();
        dto.setPassword(encoder.encode(password));

        AdviserUserDetails user = userService.registration(dto).orElseThrow();

        return cteateRegistrationResponseDto(user);
    }

    public ResponseEntity<ActivationResponseDto> activateUser(String activationCode) {

        AdviserUserDetails user =  userService.activation(activationCode).get();

        long userId = user.getId();

        Authentication trustedAuthentication = detailsService.createTrustedAuthenticationWithUserId(userId);

        SecurityContextHolder.getContext().setAuthentication(trustedAuthentication);

        String accessToken = jwtService.generateAccessToken(userId);

        return ResponseEntity
                .status(OK)
                .header(AUTHORIZATION, JwtService.TOKEN_PREFIX + accessToken)
                .body(new ActivationResponseDto());
    }



    private RegistrationResponseDto cteateRegistrationResponseDto(AdviserUserDetails user) {
        RegistrationResponseDto responseDto = new RegistrationResponseDto();
        responseDto.setUserId(user.getId());
        return responseDto;
    }
}
