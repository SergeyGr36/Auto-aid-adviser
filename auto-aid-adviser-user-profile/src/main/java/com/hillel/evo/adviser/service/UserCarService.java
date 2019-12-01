package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.entity.UserCar;

import java.util.List;

public interface UserCarService {
    UserCar getCarByUserIdAndCarId(Long userId, Long carId);
    List<UserCar> getByUserId(Long userId);
    UserCar createUserCar(UserCar car, Long userId);
    UserCar updateUserCar(UserCar car, Long userId);
    void deleteUserCar(UserCar car, Long userId);

}
