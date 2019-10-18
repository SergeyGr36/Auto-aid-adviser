package com.hillel.evo.adviser.registration.service;

import com.hillel.evo.adviser.registration.dto.RegistrationDto;
import com.hillel.evo.adviser.registration.dto.UserDto;
import com.hillel.evo.adviser.registration.entity.User;
import com.hillel.evo.adviser.registration.exception.ResourceAlreadyExistsException;
import com.hillel.evo.adviser.registration.exception.ResourceNotFoundException;
import com.hillel.evo.adviser.registration.message.Message;
import com.hillel.evo.adviser.registration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto findByMail(String mail) {
        return userRepository.findByMail(mail);
    }

    @Override
    public Map<String, Object> registration(RegistrationDto dto) {
        Boolean existUser = userRepository.existsByMail(dto.getMail());
        if (existUser) {
            throw new ResourceAlreadyExistsException(String.format(Message.USER_ALREADY_EXISTS.getDiscript(), dto.getMail()));
        }

        User user = new User();
        user.setMail(dto.getMail());
        user.setPassword(String.valueOf(dto.getPassword().hashCode()));
        user.setRole(dto.getRole());
        user.setActivationCode(UUID.randomUUID().toString());
        user.setActive(false);

        User saveUser = userRepository.save(user);
        Map<String, Object> mapResponse = new HashMap<>();
            //
            // send mail with code for user
            //
        mapResponse.put("registration", true);
        mapResponse.put("code", saveUser.getActivationCode());
        return mapResponse;
    }

    @Override
    public UserDto activation(String code) {
        User user = userRepository.findByActivationCode(code);
        if (user != null) {
            user.setActive(true);
            user.setActivationCode(null);
            user = userRepository.save(user);
            return userRepository.findByMail(user.getMail());
        } else {
            throw new ResourceNotFoundException(Message.ACTIVE_CODE_NOT_FOUND.getDiscript());
        }
    }

    @Override
    public UserDto update(UserDto userDto) {
        return null;
    }

    @Override
    public void delete(String mail) {

    }
}
