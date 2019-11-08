package com.hillel.evo.adviser.controller;


import com.hillel.evo.adviser.dto.BusinessDto;
import com.hillel.evo.adviser.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/businesses")
public class BusinessController {
    private final BusinessService businessService;

    @Autowired
    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<List<BusinessDto>> getBusinessByOwnerId(@PathVariable Long id){
        return new ResponseEntity<>(businessService.findAllByUser(id), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<BusinessDto> findBusinessById(@PathVariable Long id){
        return ResponseEntity.ok(businessService.findBusinessById(id));
    }

    @PostMapping
    public ResponseEntity<BusinessDto> createBusiness(@RequestBody final BusinessDto businessDTO){
        return ResponseEntity.ok(businessService.createBusiness(businessDTO));
    }

    @PutMapping
    public ResponseEntity<BusinessDto> updateBusiness(@RequestBody final BusinessDto businessDTO){
        return ResponseEntity.ok(businessService.updateBusiness(businessDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBusiness(@PathVariable Long id){
        businessService.deleteBusiness(id);
        return new ResponseEntity<String>("Deleted successful", HttpStatus.OK);
    }
}
