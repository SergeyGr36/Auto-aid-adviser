package com.hillel.evo.adviser.registration.service;

import com.hillel.evo.adviser.registration.dto.RegistrationDto;
import com.hillel.evo.adviser.registration.dto.UserDto;

import java.util.Map;

public interface UserService {
    UserDto findByMail(String mail);
    Map<String, Object> registration(RegistrationDto dto);
    UserDto activation(String code);
    UserDto update(UserDto userDto);
    void delete(String mail);
}
