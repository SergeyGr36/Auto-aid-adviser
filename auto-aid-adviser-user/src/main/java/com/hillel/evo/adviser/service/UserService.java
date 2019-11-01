package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.dto.AdviserUserDetailsDto;
import com.hillel.evo.adviser.dto.UserRegistrationDto;

public interface UserService {
    AdviserUserDetailsDto activation(String code);
    AdviserUserDetailsDto registration(UserRegistrationDto dto);
}
