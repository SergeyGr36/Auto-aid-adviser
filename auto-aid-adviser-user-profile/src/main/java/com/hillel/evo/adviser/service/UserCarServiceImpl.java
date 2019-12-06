package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.dto.UserCarDto;
import com.hillel.evo.adviser.entity.SimpleUser;
import com.hillel.evo.adviser.entity.UserCar;
import com.hillel.evo.adviser.mapper.UserCarMapper;
import com.hillel.evo.adviser.repository.SimpleUserRepository;
import com.hillel.evo.adviser.repository.UserCarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCarServiceImpl implements UserCarService {

    private transient final UserCarRepository repository;
    private transient final UserCarMapper mapper;
    private transient final SimpleUserRepository userRepository;

    @Autowired
    public UserCarServiceImpl(UserCarRepository repository, UserCarMapper mapper, SimpleUserRepository userRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    @Override
    public UserCarDto getCarByUserIdAndCarId(Long userId, Long carId) {
        UserCar userCar = (repository.findByUserIdAndCarId(userId, carId)).get();
        UserCarDto userCarDto = mapper.toDto(userCar);
        return userCarDto;
    }

    @Override
    public List<UserCarDto> getByUserId(Long userId) {
        return mapper.toDtoList(repository.findAllBySimpleUserId(userId));
    }

    @Override
    public UserCarDto createUserCar(UserCarDto car, Long userId) {
        SimpleUser simpleUser = userRepository.getOne(userId);
        UserCar userCar = mapper.toCar(car, simpleUser);
        UserCar save = repository.save(userCar);
        return mapper.toDto(repository.findFetchUserCar(save));
    }

    @Override
    public UserCarDto updateUserCar(UserCarDto car, Long userId) {
        return createUserCar(car, userId);
    }

    @Override
    public void deleteUserCar(Long carId, Long userId) {
        UserCar userCar = repository.findByUserIdAndCarId(userId, carId)
                .orElseThrow(() -> new RuntimeException("Car not found"));
        repository.delete(userCar);
    }


}
