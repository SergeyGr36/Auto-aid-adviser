package com.hillel.evo.adviser.userprofile.service;

import com.hillel.evo.adviser.userprofile.dto.BusinessUserRegistrationDto;
import com.hillel.evo.adviser.userprofile.dto.SimpleUserRegistrationDto;
import com.hillel.evo.adviser.userprofile.entity.AdviserUserDetails;

import java.util.Optional;

public interface UserService {
    Optional<AdviserUserDetails> activation(String code);
    Optional<AdviserUserDetails> registration(SimpleUserRegistrationDto dto);
    Optional<AdviserUserDetails> registration(BusinessUserRegistrationDto dto);
}
