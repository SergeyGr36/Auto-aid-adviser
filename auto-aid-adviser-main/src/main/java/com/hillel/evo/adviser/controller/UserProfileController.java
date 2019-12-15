package com.hillel.evo.adviser.controller;

import com.hillel.evo.adviser.dto.UserCarDto;
import com.hillel.evo.adviser.entity.AdviserUserDetails;
import com.hillel.evo.adviser.service.SecurityUserDetails;
import com.hillel.evo.adviser.service.UserCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user/profile")
public class UserProfileController {
    private final transient String ROLE = "ROLE_USER";
    @Autowired
    private transient UserCarService service;
    public UserProfileController(UserCarService service) {
        this.service = service;
    }
    @Secured(ROLE)
    @GetMapping("/{id}")
    public ResponseEntity<UserCarDto> getCarByCarId(Authentication authentication, @PathVariable Long id){
        return ResponseEntity.ok(service.getCarByUserIdAndCarId(getUserIdFromAuthentication(authentication), id));
    }
    @Secured(ROLE)
    @GetMapping
    public ResponseEntity<List<UserCarDto>> getCarsByUser(Authentication authentication){
        return ResponseEntity.ok(service.getByUserId(getUserIdFromAuthentication(authentication)));
    }
    @Secured(ROLE)
    @DeleteMapping("/cars/{id}")
    public ResponseEntity<String> deleteUserCar(@PathVariable Long id, Authentication authentication){
        service.deleteUserCar(id, getUserIdFromAuthentication(authentication));
    return new ResponseEntity<String>("Deleted successful", HttpStatus.OK);
    }

    @Secured(ROLE)
    @PostMapping("/cars")
    public ResponseEntity<UserCarDto> createUserCar(@RequestBody final UserCarDto car, Authentication authentication){
        return ResponseEntity.ok(service.createUserCar(car, getUserIdFromAuthentication(authentication)));
    }

    @Secured(ROLE)
    @PutMapping("/cars/{id}")
    public ResponseEntity<UserCarDto> updateUserCar(@PathVariable Long id, @RequestBody final UserCarDto car, Authentication authentication){
            return ResponseEntity.ok(service.updateUserCar(car, getUserIdFromAuthentication(authentication)));
    }

//    @Secured(ROLE)
//    @GetMapping("/email")
//    public ResponseEntity<String> getUsersEmail(Authentication authentication){
//        SecurityUserDetails userDetails = (SecurityUserDetails) authentication.getPrincipal();
//        Long userId = userDetails.getUserId();
//        return ResponseEntity.ok(service.getUsersEmail(userId));
//    }
    private Long getUserIdFromAuthentication(Authentication authentication) {
        SecurityUserDetails userDetails = (SecurityUserDetails) authentication.getPrincipal();
        return userDetails.getUserId();
    }
//    todo для картинок вертать url і удалить метод з емейлом
}
