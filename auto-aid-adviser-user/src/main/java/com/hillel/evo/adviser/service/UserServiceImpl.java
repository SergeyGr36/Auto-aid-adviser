package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.dto.AdviserUserDetailsDto;
import com.hillel.evo.adviser.dto.UserRegistrationDto;
import com.hillel.evo.adviser.entity.AdviserUserDetails;
import com.hillel.evo.adviser.entity.BusinessUser;
import com.hillel.evo.adviser.entity.SimpleUser;
import com.hillel.evo.adviser.enums.RoleUser;
import com.hillel.evo.adviser.exception.ActivationCodeFoundNoMatchException;
import com.hillel.evo.adviser.exception.UserAlreadyExistsRegistrationException;
import com.hillel.evo.adviser.parameter.MessageParameters;
import com.hillel.evo.adviser.repository.AdviserUserDetailRepository;
import com.hillel.evo.adviser.repository.BusinessUserRepository;
import com.hillel.evo.adviser.repository.SimpleUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final transient BusinessUserRepository businessUserRepository;
    private final transient SimpleUserRepository simpleUserRepository;
    private final transient AdviserUserDetailRepository userDetailRepository;
    private final transient EmailService emailService;

    @Value("${frontend.host}")
    private transient String frontHost;

    @Value("${frontend.router.activation}")
    private transient String routeActivation;

    public UserServiceImpl(BusinessUserRepository businessUserRepository,
                           SimpleUserRepository simpleUserRepository,
                           AdviserUserDetailRepository userDetailRepository,
                           EmailService emailService) {
        this.businessUserRepository = businessUserRepository;
        this.simpleUserRepository = simpleUserRepository;
        this.userDetailRepository = userDetailRepository;
        this.emailService = emailService;
    }

    @Override
    public AdviserUserDetailsDto activation(String code) {
        Optional<AdviserUserDetails> optional = userDetailRepository.findByActivationCode(code);
        return optional.map(adviserUserDetails -> {
            adviserUserDetails.setActive(true);
            adviserUserDetails.setActivationCode(null);
            AdviserUserDetails save = userDetailRepository.save(adviserUserDetails);
            return new AdviserUserDetailsDto(save);
        }).orElseThrow(() -> new ActivationCodeFoundNoMatchException());
    }

    @Override
    @Transactional
    public AdviserUserDetailsDto registration(UserRegistrationDto dto) {
        AdviserUserDetails userDetails = createNewAdviserUserDetails(dto);
        AdviserUserDetailsDto createUser = createUserDto(userDetails);
        sendMail(userDetails);
        return createUser;
    }

    private AdviserUserDetailsDto createUserDto(AdviserUserDetails userDetails) {
        @NotNull RoleUser role = userDetails.getRole();
        if (role == RoleUser.ROLE_BUSINESS) {
            return createBusinessUser(userDetails);     // ROLE_BUSINESS
        }
        return createSimpleUser(userDetails);           // ROLE_USER
    }

    private AdviserUserDetailsDto createSimpleUser(AdviserUserDetails userDetails) {
        SimpleUser simpleUser = new SimpleUser();
        simpleUser.setUserDetails(userDetails);
        SimpleUser saveSimpleUser = simpleUserRepository.save(simpleUser);
        return new AdviserUserDetailsDto(saveSimpleUser.getUserDetails());
    }

    private AdviserUserDetailsDto createBusinessUser(AdviserUserDetails userDetails) {
        BusinessUser businessUser = new BusinessUser();
        businessUser.setUserDetails(userDetails);
        BusinessUser saveBusinessUser = businessUserRepository.save(businessUser);
        return new AdviserUserDetailsDto(saveBusinessUser.getUserDetails());
    }

    private AdviserUserDetails createNewAdviserUserDetails(UserRegistrationDto dto) {
        if (userDetailRepository.existsByEmail(dto.getEmail())) {
            throw new UserAlreadyExistsRegistrationException();
        }

        AdviserUserDetails userDetails = new AdviserUserDetails();
        userDetails.setRole(dto.getRole());
        userDetails.setEmail(dto.getEmail());
        userDetails.setPassword(dto.getPassword());
        userDetails.setActive(false);
        userDetails.setActivationCode(UUID.randomUUID().toString());
        return userDetails;
    }

    private void sendMail(AdviserUserDetails details) {
        String link = frontHost+routeActivation+details.getActivationCode();
        MessageParameters msgParam = MessageParameters.builder()
                .toAddresses(details.getEmail())
                .nameOfTemplate("confirmation-of-registration")
                .templateParameter("userName", details.getEmail())
                .templateParameter("link", link)
                .subject("Activation code from Auto-Aid-Adviser")
                .build();
        boolean statusSend = emailService.sendMessage(msgParam);

        if (statusSend) {
            LOGGER.info(details.getEmail() + " - " + details.getActivationCode());
        } else {
            LOGGER.error("Error sending message to user mail: " + details.getEmail());
            throw new RuntimeException("Error sending message to user mail");
        }
    }
}