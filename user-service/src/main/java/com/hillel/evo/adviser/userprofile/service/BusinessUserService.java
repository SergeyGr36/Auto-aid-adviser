package com.hillel.evo.adviser.userprofile.service;

import com.hillel.evo.adviser.userprofile.dto.BusinessUserRegistrationDto;
import com.hillel.evo.adviser.userprofile.entity.AdviserUserDetails;

public interface BusinessUserService {
    AdviserUserDetails registration(BusinessUserRegistrationDto dto);
}
