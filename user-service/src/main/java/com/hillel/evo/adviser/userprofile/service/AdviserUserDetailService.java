package com.hillel.evo.adviser.userprofile.service;

import com.hillel.evo.adviser.entity.AdviserUserDetails;

public interface AdviserUserDetailService {
    AdviserUserDetails activation(String code);
}
