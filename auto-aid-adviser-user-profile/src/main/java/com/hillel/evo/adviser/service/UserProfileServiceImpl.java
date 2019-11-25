package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.entity.SimpleUser;
import com.hillel.evo.adviser.mapper.UserProfileMapper;
import com.hillel.evo.adviser.repository.SimpleUserRepository;
import com.hillel.evo.adviser.repository.UserProfileRepository;
import com.hillel.evo.adviser.dto.UserProfileDto;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements UserProfileService {
    private transient final UserProfileRepository repository;
    private transient final UserProfileMapper mapper;
    private transient final SimpleUserRepository userRepository;

    public UserProfileServiceImpl(UserProfileRepository repository, UserProfileMapper mapper, SimpleUserRepository userRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.userRepository = userRepository;
    }
    @Override
    public UserProfileDto getByUserId(Long id){
        return mapper.toDto(repository.getOne(id));
    }

    @Override
    public UserProfileDto createUserProfile(UserProfileDto dto, SimpleUser user) {
        return mapper.toDto(repository.save(mapper.toUser(dto, user)));
    }

    @Override
    public UserProfileDto updateUserProfile(UserProfileDto dto, SimpleUser user) {
        return mapper.toDto(repository.save(mapper.toUser(dto, user)));
    }

    @Override
    public void deleteUserProfile(UserProfileDto dto, SimpleUser user) {
        repository.delete(mapper.toUser(dto, user));
    }
}
