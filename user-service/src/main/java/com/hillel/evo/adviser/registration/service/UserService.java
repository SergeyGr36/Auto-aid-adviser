package com.hillel.evo.adviser.registration.service;

import com.hillel.evo.adviser.registration.dto.RegistrationDto;
import com.hillel.evo.adviser.registration.dto.UserDto;
import com.hillel.evo.adviser.registration.entity.User;

import java.util.Map;

public interface UserService {
    User findUserByMail(String mail);
    UserDto findByMail(String mail);
    Map<String, Object> registration(RegistrationDto dto);
    UserDto activation(String code);
    UserDto update(UserDto userDto);
    void delete(String mail);
}
