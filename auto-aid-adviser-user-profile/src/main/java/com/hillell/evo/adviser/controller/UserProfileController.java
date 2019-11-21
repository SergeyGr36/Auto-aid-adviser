package com.hillell.evo.adviser.controller;

import com.hillel.evo.adviser.entity.SimpleUser;
import com.hillell.evo.adviser.dto.UserProfileDto;
import com.hillell.evo.adviser.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-profile")
public class UserProfileController {
    private final transient String ROLE = "ROLE_USER";
    @Autowired
    private transient UserProfileService service;
    public UserProfileController(UserProfileService service) {
        this.service = service;
    }
    @Secured(ROLE)
    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDto> getByUserId(@PathVariable Long id){
        return ResponseEntity.ok(service.getByUserId(id));
    }
    @Secured(ROLE)
    @DeleteMapping
    public ResponseEntity<String> delete(@RequestBody UserProfileDto dto, SimpleUser user){
        service.deleteUserProfile(dto, user);
    return new ResponseEntity<String>("Deleted successful", HttpStatus.OK);
}
    @Secured(ROLE)
    @PostMapping
    public ResponseEntity<UserProfileDto> createUserProfile(@RequestBody UserProfileDto dto, SimpleUser user){
        return ResponseEntity.ok(service.createUserProfile(dto, user));
    }
    @Secured(ROLE)
    @PutMapping
    public ResponseEntity<UserProfileDto> updateUserProfile(@RequestBody UserProfileDto dto, SimpleUser user){
        return ResponseEntity.ok(service.updateUserProfile(dto, user));
    }
}
