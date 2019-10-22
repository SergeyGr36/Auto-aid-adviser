package com.hillel.evo.adviser.userprofile.service;

import com.hillel.evo.adviser.userprofile.dto.SimpleUserRegistrationDto;
import com.hillel.evo.adviser.userprofile.entity.AdviserUserDetails;
import com.hillel.evo.adviser.userprofile.entity.SimpleUser;
import com.hillel.evo.adviser.userprofile.enums.RoleUser;
import com.hillel.evo.adviser.userprofile.exception.ResourceAlreadyExistsException;
import com.hillel.evo.adviser.userprofile.message.Message;
import com.hillel.evo.adviser.userprofile.repository.AdviserUserDetailRepository;
import com.hillel.evo.adviser.userprofile.repository.SimpleUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SimpleUserServiceImpl implements SimpleUserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleUserServiceImpl.class);

    private transient final SimpleUserRepository userRepository;
    private transient final AdviserUserDetailRepository userDetailRepository;

    @Autowired
    public SimpleUserServiceImpl(SimpleUserRepository userRepository, AdviserUserDetailRepository userDetailRepository) {
        this.userRepository = userRepository;
        this.userDetailRepository = userDetailRepository;
    }

    @Override
    public AdviserUserDetails registration(SimpleUserRegistrationDto dto) {
        if (userDetailRepository.existsByEmail(dto.getEmail())) {
            throw new ResourceAlreadyExistsException(String.format(Message.USER_ALREADY_EXISTS.getDiscript(), dto.getEmail()));
        }

        AdviserUserDetails userDetails = new AdviserUserDetails();
        userDetails.setRole(RoleUser.ROLE_USER);
        userDetails.setEmail(dto.getEmail());
        userDetails.setPassword(dto.getPassword());
        userDetails.setActive(false);
        userDetails.setActivationCode(UUID.randomUUID().toString());

        SimpleUser simpleUser = new SimpleUser();
        simpleUser.setUserDetails(userDetails);

        SimpleUser saveSimpleUser = userRepository.save(simpleUser);
        /*
         send email to user with code activation
        */
        LOGGER.info("========================================================");
        LOGGER.info(saveSimpleUser.getUserDetails().getEmail() + " - " + saveSimpleUser.getUserDetails().getActivationCode());
        LOGGER.info("========================================================");

        return saveSimpleUser.getUserDetails();
    }
}
