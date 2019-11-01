package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.dto.BusinessUserRegistrationDto;
import com.hillel.evo.adviser.dto.SimpleUserRegistrationDto;
import com.hillel.evo.adviser.entity.AdviserUserDetails;

import java.util.Optional;

public interface UserService {
    Optional<AdviserUserDetails> activation(String code);
    Optional<AdviserUserDetails> registration(SimpleUserRegistrationDto dto);
    Optional<AdviserUserDetails> registration(BusinessUserRegistrationDto dto);
}
