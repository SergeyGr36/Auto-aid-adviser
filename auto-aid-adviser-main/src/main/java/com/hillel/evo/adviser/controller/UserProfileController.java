package com.hillel.evo.adviser.controller;

import com.hillel.evo.adviser.dto.ImageDto;
import com.hillel.evo.adviser.dto.SimpleUserDto;
import com.hillel.evo.adviser.dto.UserCarDto;
import com.hillel.evo.adviser.service.SecurityUserDetails;
import com.hillel.evo.adviser.service.SimpleUserService;
import com.hillel.evo.adviser.service.UserCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/user/profile")
public class UserProfileController {

    private final static String ROLE_USER = "ROLE_USER";
    private final static String ROLE_BUSINESS = "ROLE_BUSINESS";

    private final transient UserCarService userCarService;
    private final SimpleUserService simpleUserService;

    @Autowired
    public UserProfileController(UserCarService service, SimpleUserService simpleUserService) {
        this.userCarService = service;
        this.simpleUserService = simpleUserService;
    }

    @Secured({ROLE_USER, ROLE_BUSINESS})
    @GetMapping("/car/{id}")
    public UserCarDto getCarByCarId(@PathVariable Long id){
        UserCarDto userCarDto = userCarService.getCarByUserIdAndCarId(id);
        return userCarDto;
    }

    @Secured(ROLE_USER)
    @GetMapping("/cars")
    public List<UserCarDto> getCarsByUser(Authentication authentication){
        Long userId = getUserIdFromAuthentication(authentication);
        List<UserCarDto> carDtos = userCarService.getByUserId(userId);
        return carDtos;
    }

    @Secured(ROLE_USER)
    @DeleteMapping("/car/{id}")
    public ResponseEntity<String> deleteUserCar(@PathVariable Long id, Authentication authentication){
        Long userId = getUserIdFromAuthentication(authentication);
        userCarService.deleteUserCar(id, userId);
    return new ResponseEntity<String>("Deleted successful", HttpStatus.OK);
    }

    @Secured(ROLE_USER)
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(path = "/car",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserCarDto createUserCar(@RequestPart(name = "json") @Validated final UserCarDto car,
                                              @RequestPart(name = "files") List<MultipartFile> files,
                                              Authentication authentication) {
        Long userId = getUserIdFromAuthentication(authentication);
        UserCarDto userCar = userCarService.createUserCar(userId, car, files);
        return userCar;
    }

    @Secured(ROLE_USER)
    @PutMapping("/car")
    public UserCarDto updateUserCar(@RequestBody final UserCarDto car, Authentication authentication){
        Long userId = getUserIdFromAuthentication(authentication);
        UserCarDto updateCar = userCarService.updateUserCar(car, userId);
        return updateCar;
    }

    @Secured({ROLE_USER, ROLE_BUSINESS})
    @GetMapping
    public SimpleUserDto getSimpleUser(Authentication authentication){
        Long userId = getUserIdFromAuthentication(authentication);
        return simpleUserService.findById(userId);
    }

    @Secured(ROLE_USER)
    @PutMapping
    public ResponseEntity<SimpleUserDto> updateSimpleUser(@RequestBody final SimpleUserDto userDto, Authentication authentication){
        Long userId = getUserIdFromAuthentication(authentication);
        userDto.setId(userId);
        return new ResponseEntity<SimpleUserDto>(simpleUserService.update(userDto), HttpStatus.OK);
    }

    private Long getUserIdFromAuthentication(Authentication authentication) {
        SecurityUserDetails userDetails = (SecurityUserDetails) authentication.getPrincipal();
        return userDetails.getUserId();
    }

    // === Images Car ===

    @Secured(ROLE_USER)
    @PostMapping("/car/{carId}/image")
    public ImageDto addImageForUserCar(@RequestPart(name = "file") MultipartFile image,
                                       Authentication authentication,
                                       @PathVariable("carId") Long carId) {
        Long userId = getUserIdFromAuthentication(authentication);
        return userCarService.addImage(userId, carId, image);
    }

    @Secured(ROLE_USER)
    @DeleteMapping("/car/{carId}/image/{imageId}")
    public ResponseEntity<String> addImageForUserCar(Authentication authentication,
                                       @PathVariable("carId") Long carId,
                                       @PathVariable("imageId") Long imageId) {
        Long userId = getUserIdFromAuthentication(authentication);
        boolean isDeleteImage = userCarService.deleteImage(userId, carId, imageId);
        if (isDeleteImage) {
            return new ResponseEntity<>("Image deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Image not deleted", HttpStatus.BAD_REQUEST);
        }
    }

}
