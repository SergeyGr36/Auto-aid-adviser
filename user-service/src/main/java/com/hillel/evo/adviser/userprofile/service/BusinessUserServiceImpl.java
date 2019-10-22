package com.hillel.evo.adviser.userprofile.service;

import com.hillel.evo.adviser.userprofile.dto.BusinessUserRegistrationDto;
import com.hillel.evo.adviser.userprofile.entity.AdviserUserDetails;
import com.hillel.evo.adviser.userprofile.entity.BusinessUser;
import com.hillel.evo.adviser.userprofile.enums.RoleUser;
import com.hillel.evo.adviser.userprofile.exception.ResourceAlreadyExistsException;
import com.hillel.evo.adviser.userprofile.message.Message;
import com.hillel.evo.adviser.userprofile.repository.AdviserUserDetailRepository;
import com.hillel.evo.adviser.userprofile.repository.BusinessUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BusinessUserServiceImpl implements BusinessUserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessUserServiceImpl.class);

    private final transient BusinessUserRepository userRepository;
    private final transient AdviserUserDetailRepository userDetailRepository;

    @Autowired
    public BusinessUserServiceImpl(BusinessUserRepository userRepository, AdviserUserDetailRepository userDetailRepository) {
        this.userRepository = userRepository;
        this.userDetailRepository = userDetailRepository;
    }

    @Override
    public AdviserUserDetails registration(BusinessUserRegistrationDto dto) {
        //check user to databases
        if (userDetailRepository.existsByEmail(dto.getEmail())) {
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
        LOGGER.info("========================================================");
        LOGGER.info(saveBusinessUser.getUserDetails().getEmail() + " - " + saveBusinessUser.getUserDetails().getActivationCode());
        LOGGER.info("========================================================");

        return saveBusinessUser.getUserDetails();
    }
}
