package com.hillel.evo.adviser.controller;

import com.hillel.evo.adviser.dto.BusinessUserRegistrationDto;
import com.hillel.evo.adviser.dto.UserRegistrationDto;
import com.hillel.evo.adviser.entity.AdviserUserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    @PostMapping("/user")
    public AdviserUserDetails userDetails(UserRegistrationDto user) {
        return new AdviserUserDetails();
    }

    @PostMapping("/admin")
    public AdviserUserDetails businessUserDetails(BusinessUserRegistrationDto user) {
        return new AdviserUserDetails();
    }
}
