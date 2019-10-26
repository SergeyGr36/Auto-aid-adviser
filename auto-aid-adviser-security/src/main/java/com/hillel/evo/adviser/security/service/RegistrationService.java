package com.hillel.evo.adviser.security.service;

import com.hillel.evo.adviser.dto.BusinessUserRegistrationDto;
import com.hillel.evo.adviser.dto.SimpleUserRegistrationDto;
import com.hillel.evo.adviser.entity.AdviserUserDetails;
import com.hillel.evo.adviser.security.dto.ActivationResponseDto;
import com.hillel.evo.adviser.security.dto.RegistrationResponseDto;
import com.hillel.evo.adviser.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.OK;

@Service
public class RegistrationService {

    final private transient UserService userService;
    final private transient PasswordEncoder encoder;

    public RegistrationService(UserService userService, PasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }

    public RegistrationResponseDto registerSimpleUser(SimpleUserRegistrationDto dto) {
        String password = dto.getPassword();
        dto.setPassword(encoder.encode(password));

        AdviserUserDetails user = userService.registration(dto).orElseThrow();

        return cteateRegistrationResponseDto(user);
    }

    public RegistrationResponseDto registerBusinessUser(BusinessUserRegistrationDto dto) {
        String password = dto.getPassword();
        dto.setPassword(encoder.encode(password));

        AdviserUserDetails user = userService.registration(dto).orElseThrow();

        return cteateRegistrationResponseDto(user);
    }

    public ResponseEntity<ActivationResponseDto> activateUser(String activationCode) {
        return ResponseEntity.status(OK).body(new ActivationResponseDto());
    }

    private RegistrationResponseDto cteateRegistrationResponseDto(AdviserUserDetails user) {
        RegistrationResponseDto responseDto = new RegistrationResponseDto();
        responseDto.setUserId(user.getId());
        return responseDto;
    }
}
