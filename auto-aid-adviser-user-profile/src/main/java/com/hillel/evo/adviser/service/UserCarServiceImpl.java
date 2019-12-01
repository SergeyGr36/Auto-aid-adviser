package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.entity.UserCar;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCarServiceImpl implements UserCarService {

//    private transient final UserProfileRepository repository;
//    private transient final UserProfileMapper mapper;
//    private transient final SimpleUserRepository userRepository;
//
//    public UserCarServiceImpl(UserProfileRepository repository, UserProfileMapper mapper, SimpleUserRepository userRepository) {
//        this.repository = repository;
//        this.mapper = mapper;
//        this.userRepository = userRepository;
//    }
    @Override
    public UserCar getCarByUserIdAndCarId(Long userId, Long carId) {
        return null;
    }

    @Override
    public List<UserCar> getByUserId(Long userId) {
        return null;
    }

    @Override
    public UserCar createUserCar(UserCar car, Long userId) {
        return null;
    }

    @Override
    public UserCar updateUserCar(UserCar car, Long userId) {
        return null;
    }

    @Override
    public void deleteUserCar(UserCar car, Long userId) {

    }


}
