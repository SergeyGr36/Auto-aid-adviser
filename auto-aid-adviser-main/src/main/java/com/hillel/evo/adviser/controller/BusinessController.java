package com.hillel.evo.adviser.controller;


import com.hillel.evo.adviser.dto.BusinessDto;
import com.hillel.evo.adviser.repository.AdviserUserDetailRepository;
import com.hillel.evo.adviser.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private AdviserUserDetailRepository userRepo;

    private transient final BusinessService businessService;

    @Autowired
    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @Secured(value = "ROLE_BUSINESS")
    @GetMapping
    public ResponseEntity<List<BusinessDto>> getBusinessByOwnerId(Principal principal){
        Long userId = getUserIdByMail(principal.getName());
        return new ResponseEntity<>(businessService.findAllByUser(userId), HttpStatus.OK);
    }

    @Secured(value = "ROLE_BUSINESS")
    @GetMapping("/{id}")
    public ResponseEntity<BusinessDto> findBusinessById(@PathVariable Long id, Principal principal){
        Long userId = getUserIdByMail(principal.getName());
        return ResponseEntity.ok(businessService.findBusinessById(id, userId));
    }

    @Secured(value = "ROLE_BUSINESS")
    @PostMapping
    public ResponseEntity<BusinessDto> createBusiness(@Validated @RequestBody final BusinessDto businessDTO, Principal principal){
        Long userId = getUserIdByMail(principal.getName());
        return ResponseEntity.ok(businessService.createBusiness(businessDTO, userId));
    }

    @Secured(value = "ROLE_BUSINESS")
    @PutMapping
    public ResponseEntity<BusinessDto> updateBusiness(@Validated @RequestBody final BusinessDto businessDTO, Principal principal){
        Long userId = getUserIdByMail(principal.getName());
        return ResponseEntity.ok(businessService.updateBusiness(businessDTO, userId));
    }

    @Secured(value = "ROLE_BUSINESS")
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
