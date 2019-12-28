package com.hillel.evo.adviser.service.impl;

import com.hillel.evo.adviser.dto.SimpleUserDto;
import com.hillel.evo.adviser.entity.SimpleUser;
import com.hillel.evo.adviser.exception.ResourceNotFoundException;
import com.hillel.evo.adviser.mapper.SimpleUserMapper;
import com.hillel.evo.adviser.repository.SimpleUserRepository;
import com.hillel.evo.adviser.service.SimpleUserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SimpleUserServiceImpl implements SimpleUserService {

    private final SimpleUserRepository simpleUserRepository;
    private final SimpleUserMapper simpleUserMapper;

    public SimpleUserServiceImpl(SimpleUserRepository simpleUserRepository, SimpleUserMapper simpleUserMapper) {
        this.simpleUserRepository = simpleUserRepository;
        this.simpleUserMapper = simpleUserMapper;
    }

    @Override
    public SimpleUserDto findById(Long id) {
        Optional<SimpleUser> simpleUser = simpleUserRepository.findById(id);
        return simpleUser.map(user -> simpleUserMapper.toDto(user))
                .orElseThrow(() -> new ResourceNotFoundException("Data user not found by id: " + id));
    }

    @Override
    public SimpleUserDto update(SimpleUserDto userDto) {
        SimpleUser user = simpleUserMapper.toEntity(userDto);
        SimpleUser save = simpleUserRepository.save(user);
        return simpleUserMapper.toDto(save);
    }
}
