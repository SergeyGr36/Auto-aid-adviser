package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.entity.SimpleUser;
import com.hillel.evo.adviser.dto.UserProfileDto;

public interface UserProfileService {
    UserProfileDto getByUserId(Long id);
    UserProfileDto createUserProfile(UserProfileDto dto, SimpleUser user);
    UserProfileDto updateUserProfile(UserProfileDto dto, SimpleUser user);
    void deleteUserProfile(UserProfileDto dto, SimpleUser user);

}
