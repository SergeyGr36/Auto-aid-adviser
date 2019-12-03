package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.dto.UserCarDto;
import com.hillel.evo.adviser.entity.UserCar;

import java.util.List;

public interface UserCarService {
    UserCarDto getCarByUserIdAndCarId(Long userId, Long carId);
    List<UserCarDto> getByUserId(Long userId);
    UserCarDto createUserCar(UserCarDto car, Long userId);
    UserCarDto updateUserCar(UserCarDto car, Long userId);
    void deleteUserCar(UserCarDto car, Long userId);

}
