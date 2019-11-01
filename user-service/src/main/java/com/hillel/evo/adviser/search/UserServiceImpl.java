package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.entity.BusinessUser;
import com.hillel.evo.adviser.dto.BusinessUserRegistrationDto;
import com.hillel.evo.adviser.dto.SimpleUserRegistrationDto;
import com.hillel.evo.adviser.dto.UserRegistrationDto;
import com.hillel.evo.adviser.entity.AdviserUserDetails;
import com.hillel.evo.adviser.entity.SimpleUser;
import com.hillel.evo.adviser.enums.RoleUser;
import com.hillel.evo.adviser.repository.AdviserUserDetailRepository;
import com.hillel.evo.adviser.repository.BusinessUserRepository;
import com.hillel.evo.adviser.repository.SimpleUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final transient BusinessUserRepository businessUserRepository;
    private final transient SimpleUserRepository simpleUserRepository;
    private final transient AdviserUserDetailRepository userDetailRepository;

    public UserServiceImpl(BusinessUserRepository businessUserRepository, SimpleUserRepository simpleUserRepository, AdviserUserDetailRepository userDetailRepository) {
        this.businessUserRepository = businessUserRepository;
        this.simpleUserRepository = simpleUserRepository;
        this.userDetailRepository = userDetailRepository;
    }

    @Override
    public Optional<AdviserUserDetails> activation(String code) {
        Optional<AdviserUserDetails> optional = userDetailRepository.findByActivationCode(code);
        return optional.map(adviserUserDetails -> {
            adviserUserDetails.setActive(true);
            adviserUserDetails.setActivationCode(null);
            return userDetailRepository.save(adviserUserDetails);
        });
    }

    @Override
    public Optional<AdviserUserDetails> registration(SimpleUserRegistrationDto dto) {
        Optional<AdviserUserDetails> optional = createNewAdviserUserDetails(dto);
        return optional.map(userDetail -> {
            SimpleUser simpleUser = new SimpleUser();
            simpleUser.setUserDetails(userDetail);
            // ... здесь будут заполнятся поля указаные при регистрации и касающиеся только обычного пользователя
            SimpleUser saveSimpleUser = simpleUserRepository.save(simpleUser);
            sendMail(saveSimpleUser.getUserDetails());
            return saveSimpleUser.getUserDetails();
        });
    }

    @Override
    public Optional<AdviserUserDetails> registration(BusinessUserRegistrationDto dto) {
        Optional<AdviserUserDetails> optional = createNewAdviserUserDetails(dto);
        return optional.map(userDetail -> {
            BusinessUser businessUser = new BusinessUser();
            businessUser.setUserDetails(userDetail);
            // ... здесь будут заполнятся поля для бизнеса указаные при регистрации и касающиеся только бизнеса
            BusinessUser saveBusinessUser = businessUserRepository.save(businessUser);
            sendMail(saveBusinessUser.getUserDetails());
            return saveBusinessUser.getUserDetails();
        });
    }

    private Optional<AdviserUserDetails> createNewAdviserUserDetails(UserRegistrationDto dto) {
        if (userDetailRepository.existsByEmail(dto.getEmail())) {
            return Optional.empty();
        }
        AdviserUserDetails userDetails = new AdviserUserDetails();
        userDetails.setRole(RoleUser.ROLE_USER);
        userDetails.setEmail(dto.getEmail());
        userDetails.setPassword(dto.getPassword());
        userDetails.setActive(false);
        userDetails.setActivationCode(UUID.randomUUID().toString());
        return Optional.of(userDetails);
    }

    private void sendMail(AdviserUserDetails details) {
        LOGGER.info("========================================================");
        LOGGER.info(details.getEmail() + " - " + details.getActivationCode());
        LOGGER.info("========================================================");
    }
}
