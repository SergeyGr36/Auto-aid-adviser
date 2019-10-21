package com.hillel.evo.adviser.userprofile.service;

import com.hillel.evo.adviser.dto.BusinessUserRegistrationDto;
import com.hillel.evo.adviser.entity.AdviserUserDetails;

public interface BusinessUserService {
    AdviserUserDetails registration(BusinessUserRegistrationDto dto);
}
