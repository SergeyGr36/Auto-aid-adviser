package com.hillel.evo.adviser.registration.services;

import com.hillel.evo.adviser.registration.dtos.UserDto;

public interface UserService {
    UserDto findByMail(String mail);
    UserDto registration(UserDto userDto);
    Boolean activation(String code);
    UserDto update(UserDto userDto);
    void delete(String mail);
}
