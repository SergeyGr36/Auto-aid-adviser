package com.hillel.evo.adviser.controller;

import com.hillel.evo.adviser.dto.UserCarDto;
import com.hillel.evo.adviser.entity.UserCar;
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

import java.util.List;

@RestController
@RequestMapping("/user-car")
public class UserCarController {
    private final transient String ROLE = "ROLE_USER";
    @Autowired
    private transient UserCarService service;
    public UserCarController(UserCarService service) {
        this.service = service;
    }

    @Secured(ROLE)
    @GetMapping("/{userId}/{id}")
    public ResponseEntity<UserCarDto> getCarByUserIdAndCarId(@PathVariable Long userId, @PathVariable Long id){
        return ResponseEntity.ok(service.getCarByUserIdAndCarId(userId, id));
    }

    @Secured(ROLE)
    @GetMapping("/{id}")
    public ResponseEntity<List<UserCarDto>> getCarsByUserId(@PathVariable Long id){
        return ResponseEntity.ok(service.getByUserId(id));
    }

    @Secured(ROLE)
    @DeleteMapping
    public ResponseEntity<String> deleteUserCar(@RequestBody UserCarDto car, Authentication authentication){
        service.deleteUserCar(car.getId(), getUserFromAuthentication(authentication));
    return new ResponseEntity<String>("Deleted successful", HttpStatus.OK);
    }

    @Secured(ROLE)
    @PostMapping
    public ResponseEntity<UserCarDto> createUserCar(@RequestBody final UserCarDto car, Authentication authentication){
        return ResponseEntity.ok(service.createUserCar(car, getUserFromAuthentication(authentication)));
    }

    @Secured(ROLE)
    @PutMapping
    public ResponseEntity<UserCarDto> updateUserCar(@RequestBody final UserCarDto car, Authentication authentication){
            return ResponseEntity.ok(service.updateUserCar(car, getUserFromAuthentication(authentication)));
    }

    private Long getUserFromAuthentication(Authentication authentication) {
        SecurityUserDetails userDetails = (SecurityUserDetails) authentication.getPrincipal();
        return userDetails.getUserId();
    }
}
