package com.hillel.evo.adviser.userprofile.service;

import com.hillel.evo.adviser.dto.SimpleUserRegistrationDto;
import com.hillel.evo.adviser.entity.AdviserUserDetails;

public interface SimpleUserService {
    AdviserUserDetails registration(SimpleUserRegistrationDto dto);
}
