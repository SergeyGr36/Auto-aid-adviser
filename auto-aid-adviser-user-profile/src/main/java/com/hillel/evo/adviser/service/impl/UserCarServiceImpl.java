package com.hillel.evo.adviser.service.impl;

import com.hillel.evo.adviser.dto.ImageDto;
import com.hillel.evo.adviser.dto.SimpleUserDto;
import com.hillel.evo.adviser.dto.UserCarDto;
import com.hillel.evo.adviser.entity.Image;
import com.hillel.evo.adviser.entity.SimpleUser;
import com.hillel.evo.adviser.entity.UserCar;
import com.hillel.evo.adviser.exception.DeleteException;
import com.hillel.evo.adviser.exception.ResourceNotFoundException;
import com.hillel.evo.adviser.mapper.ImageMapper;
import com.hillel.evo.adviser.mapper.SimpleUserMapper;
import com.hillel.evo.adviser.mapper.UserCarMapper;
import com.hillel.evo.adviser.repository.SimpleUserRepository;
import com.hillel.evo.adviser.repository.UserCarRepository;
import com.hillel.evo.adviser.service.UserCarService;
import com.hillel.evo.adviser.service.interfaces.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class UserCarServiceImpl implements UserCarService {

    private transient final UserCarRepository repository;

    private transient final UserCarMapper mapper;

    private transient final SimpleUserRepository userRepository;
    private transient final ImageMapper imageMapper;
    private transient final ImageService imageService;
    private final SimpleUserMapper simpleUserMapper;

    @Autowired
    public UserCarServiceImpl(UserCarRepository repository, UserCarMapper mapper, SimpleUserRepository userRepository, ImageMapper imageMapper, ImageService imageService, SimpleUserMapper simpleUserMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.imageMapper = imageMapper;
        this.imageService = imageService;
        this.simpleUserMapper = simpleUserMapper;
    }

    @Override
    public UserCarDto getCarByUserIdAndCarId(Long carId) {
        Optional<UserCar> userCar = repository.findByCarId(carId);
        UserCarDto userCarDto = userCar.map(car -> mapper.toDto(car))
                .orElseThrow(() -> new ResourceNotFoundException("Car user not found by car id: " + carId));
        return userCarDto;
    }

    @Override
    public List<UserCarDto> getByUserId(Long userId) {
        List<UserCar> list = repository.findAllBySimpleUserId(userId);
        return mapper.toDtoList(list);
    }

    @Override
    public UserCarDto createUserCar(UserCarDto car, Long userId) {
        SimpleUser simpleUser = userRepository.getOne(userId);
        UserCar userCar = mapper.toCar(car, simpleUser);
        repository.save(userCar);
        return mapper.toDto(userCar);
    }

    @Override
    public UserCarDto updateUserCar(UserCarDto car, Long userId) {
        return createUserCar(car, userId);
    }

    @Override
    @Transactional
    public void deleteUserCar(Long carId, Long userId) {
        UserCar userCar = repository.findByUserIdAndCarId(userId, carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found"));
        repository.delete(userCar);
    }

    @Override
    public List<ImageDto> findImagesByUserCarId(Long userCarId) {
        List<Image> images = repository.findImagesByUserCarId(userCarId);
        return imageMapper.toListDto(images);
    }

    @Override
    @Transactional
    public ImageDto addImage(Long userId, Long userCarId, MultipartFile file) {
        UserCar userCar = repository.findByUserIdAndCarId(userId, userCarId)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found"));
        Image image = imageService.create(userId, userCarId, file)
                .orElseThrow(() -> new ResourceNotFoundException("Image not saved"));
        userCar.getImages().add(image);
        repository.save(userCar);
        return imageMapper.toDto(image);
    }

    @Override
    public boolean deleteImage(Long userId, Long userCarId, ImageDto dto) {
        Image image = repository.findImageByUserIdAndUserCarIdAndImageId(userId, userCarId, dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Image not found"));
        return imageService.delete(image);
    }

    @Override
    public SimpleUserDto getUserByUserCarId(Long id) {
        Optional<SimpleUser> simpleUser = repository.findSimpleUserByUserCardId(id);
        SimpleUserDto simpleUserDto = simpleUser
                .map(su -> simpleUserMapper.toDto(su))
                .orElseThrow(() -> new ResourceNotFoundException("Data user not found by car id: " + id));
        return simpleUserDto;
    }
}
