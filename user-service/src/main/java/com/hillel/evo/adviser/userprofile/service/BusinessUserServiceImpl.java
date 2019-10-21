package com.hillel.evo.adviser.userprofile.service;

import com.hillel.evo.adviser.dto.BusinessUserRegistrationDto;
import com.hillel.evo.adviser.entity.AdviserUserDetails;
import com.hillel.evo.adviser.entity.BusinessUser;
import com.hillel.evo.adviser.enums.RoleUser;
import com.hillel.evo.adviser.userprofile.exception.ResourceAlreadyExistsException;
import com.hillel.evo.adviser.userprofile.message.Message;
import com.hillel.evo.adviser.userprofile.repository.AdviserUserDetailRepository;
import com.hillel.evo.adviser.userprofile.repository.BusinessUserRepository;

import java.util.UUID;

public class BusinessUserServiceImpl implements BusinessUserService {
    private final BusinessUserRepository userRepository;
    private final AdviserUserDetailRepository userDetailRepository;

    public BusinessUserServiceImpl(BusinessUserRepository userRepository, AdviserUserDetailRepository userDetailRepository) {
        this.userRepository = userRepository;
        this.userDetailRepository = userDetailRepository;
    }

    @Override
    public AdviserUserDetails registration(BusinessUserRegistrationDto dto) {
        Boolean existUser = userDetailRepository.existsByMail(dto.getEmail());
        if (existUser) {
            throw new ResourceAlreadyExistsException(String.format(Message.USER_ALREADY_EXISTS.getDiscript(), dto.getEmail()));
        }

        AdviserUserDetails userDetails = new AdviserUserDetails();
        userDetails.setRole(RoleUser.ROLE_USER);
        userDetails.setMail(dto.getEmail());
        userDetails.setPassword(dto.getPassword());
        userDetails.setActive(false);
        userDetails.setActivationCode(UUID.randomUUID().toString());

        BusinessUser businessUser = new BusinessUser();
        businessUser.setUserDetails(userDetails);

        BusinessUser saveBusinessUser = userRepository.save(businessUser);
        /*
         send email to user with code activation
        */

        return saveBusinessUser.getUserDetails();
    }
}
