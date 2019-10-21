package com.hillel.evo.adviser.userprofile.service;

import com.hillel.evo.adviser.userprofile.dto.BusinessUserRegistrationDto;
import com.hillel.evo.adviser.userprofile.entity.AdviserUserDetails;
import com.hillel.evo.adviser.userprofile.entity.BusinessUser;
import com.hillel.evo.adviser.userprofile.enums.RoleUser;
import com.hillel.evo.adviser.userprofile.exception.ResourceAlreadyExistsException;
import com.hillel.evo.adviser.userprofile.message.Message;
import com.hillel.evo.adviser.userprofile.repository.AdviserUserDetailRepository;
import com.hillel.evo.adviser.userprofile.repository.BusinessUserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BusinessUserServiceImpl implements BusinessUserService {
    private final BusinessUserRepository userRepository;
    private final AdviserUserDetailRepository userDetailRepository;

    public BusinessUserServiceImpl(BusinessUserRepository userRepository, AdviserUserDetailRepository userDetailRepository) {
        this.userRepository = userRepository;
        this.userDetailRepository = userDetailRepository;
    }

    @Override
    public AdviserUserDetails registration(BusinessUserRegistrationDto dto) {
        //check user to databases
        Boolean existUser = userDetailRepository.existsByEmail(dto.getEmail());
        if (existUser) {
            throw new ResourceAlreadyExistsException(String.format(Message.USER_ALREADY_EXISTS.getDiscript(), dto.getEmail()));
        }
        // if not found create user
        AdviserUserDetails userDetails = new AdviserUserDetails();
        userDetails.setRole(RoleUser.ROLE_BUSINESS);
        userDetails.setEmail(dto.getEmail());
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
