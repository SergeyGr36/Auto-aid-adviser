package com.hillel.evo.adviser.controller;


import com.hillel.evo.adviser.dto.BusinessDto;
import com.hillel.evo.adviser.enums.RoleUser;
import com.hillel.evo.adviser.repository.AdviserUserDetailRepository;
import com.hillel.evo.adviser.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/businesses")
public class BusinessController {

    private transient final String ROLE_BUSINESS = "ROLE_BUSINESS";

    private transient final AdviserUserDetailRepository userRepo;
    private transient final BusinessService businessService;

    @Autowired
    public BusinessController(AdviserUserDetailRepository userRepo, BusinessService businessService) {
        this.userRepo = userRepo;
        this.businessService = businessService;
    }

    @Secured(ROLE_BUSINESS)
    @GetMapping
    public ResponseEntity<List<BusinessDto>> getBusiness(Principal principal){
        Long userId = getUserIdByMail(principal.getName());
        return new ResponseEntity<>(businessService.findAllByUser(userId), HttpStatus.OK);
    }

    @Secured(ROLE_BUSINESS)
    @GetMapping("/{id}")
    public ResponseEntity<BusinessDto> findBusinessById(@PathVariable Long id, Principal principal){
        Long userId = getUserIdByMail(principal.getName());
        return ResponseEntity.ok(businessService.findBusinessById(id, userId));
    }

    @Secured(ROLE_BUSINESS)
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BusinessDto> createBusiness(@Validated @RequestBody final BusinessDto businessDTO, Principal principal){
        Long userId = getUserIdByMail(principal.getName());
        return ResponseEntity.ok(businessService.createBusiness(businessDTO, userId));
    }

    @Secured(ROLE_BUSINESS)
    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BusinessDto> updateBusiness(@Validated @RequestBody final BusinessDto businessDTO, Principal principal){
        Long userId = getUserIdByMail(principal.getName());
        return ResponseEntity.ok(businessService.updateBusiness(businessDTO, userId));
    }

    @Secured(ROLE_BUSINESS)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBusiness(@PathVariable Long id, Principal principal){
        Long userId = getUserIdByMail(principal.getName());
        businessService.deleteBusiness(id, userId);
        return new ResponseEntity<String>("Deleted successful", HttpStatus.OK);
    }

    private Long getUserIdByMail(String mail) {
       return userRepo.findByEmail(mail).get().getId();
    }
}
