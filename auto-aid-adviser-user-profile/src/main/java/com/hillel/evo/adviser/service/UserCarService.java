package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.dto.ImageDto;
import com.hillel.evo.adviser.dto.UserCarDto;
import com.hillel.evo.adviser.dto.identification.CarModelDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserCarService {
    UserCarDto getCarByUserIdAndCarId(Long userId, Long carId);
    List<UserCarDto> getByUserId(Long userId);
    UserCarDto createUserCar(UserCarDto car, Long userId);
    UserCarDto updateUserCar(UserCarDto car, Long userId);
    void deleteUserCar(Long carId, Long userId);
    List<ImageDto> findImagesByUserCarId(Long userCarId);
    ImageDto addImage(Long userId, Long userCarId, MultipartFile file);
    boolean deleteImage(Long userId, Long userCarId, ImageDto dto);
//    String getUsersEmail(Long id);
}
