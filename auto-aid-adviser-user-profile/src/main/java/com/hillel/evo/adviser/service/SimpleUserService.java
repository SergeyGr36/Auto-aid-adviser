package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.dto.SimpleUserDto;

public interface SimpleUserService {

    SimpleUserDto findById(Long id);
    SimpleUserDto update(SimpleUserDto userDto);
}
