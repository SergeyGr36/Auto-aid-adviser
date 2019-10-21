package com.hillel.evo.adviser.userprofile.service;

import com.hillel.evo.adviser.userprofile.dto.SimpleUserRegistrationDto;
import com.hillel.evo.adviser.userprofile.entity.AdviserUserDetails;

public interface SimpleUserService {
    AdviserUserDetails registration(SimpleUserRegistrationDto dto);
}
