package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.dto.ImageDto;
import com.hillel.evo.adviser.dto.SimpleUserDto;
import com.hillel.evo.adviser.dto.UserCarDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserCarService {
    UserCarDto getCarByUserIdAndCarId(Long carId);
    List<UserCarDto> getByUserId(Long userId);
    UserCarDto createUserCar(Long userId, UserCarDto car, List<MultipartFile> files);
    UserCarDto updateUserCar(UserCarDto car, Long userId);
    void deleteUserCar(Long carId, Long userId);
    List<ImageDto> findImagesByUserCarId(Long userCarId);
    ImageDto addImage(Long userId, Long userCarId, MultipartFile file);
    boolean deleteImage(Long userId, Long userCarId, Long imageId);
    SimpleUserDto getUserByUserCarId(Long id);
}
