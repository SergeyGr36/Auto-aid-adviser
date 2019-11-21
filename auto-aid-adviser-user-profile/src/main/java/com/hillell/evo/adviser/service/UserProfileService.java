package com.hillell.evo.adviser.service;

import com.hillel.evo.adviser.entity.SimpleUser;
import com.hillell.evo.adviser.dto.UserProfileDto;

public interface UserProfileService {
    UserProfileDto getByUserId(Long id);
    UserProfileDto createUserProfile(UserProfileDto dto, SimpleUser user);
    UserProfileDto updateUserProfile(UserProfileDto dto, SimpleUser user);
    void deleteUserProfile(UserProfileDto dto, SimpleUser user);

}
