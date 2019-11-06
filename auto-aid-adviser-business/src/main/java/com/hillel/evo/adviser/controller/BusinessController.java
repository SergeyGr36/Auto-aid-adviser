package com.hillel.evo.adviser.controller;


import com.hillel.evo.adviser.dto.BusinessDto;
import com.hillel.evo.adviser.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/business")
public class BusinessController {
    private final BusinessService businessService;

    @Autowired
    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<BusinessDto>> getBusinessByOwnerId(@PathVariable Long id){
        return new ResponseEntity<>(businessService.findAllByUser(id), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<BusinessDto> getBusinessById(@PathVariable Long id){
        return new ResponseEntity<BusinessDto>(businessService.getBusinessById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BusinessDto> createBusiness(@RequestBody final BusinessDto businessDTO){
        return ResponseEntity.ok(businessService.createBusiness(businessDTO));
    }
    @PutMapping
    public ResponseEntity<BusinessDto> updateBusiness(@RequestBody final BusinessDto businessDTO){
        return ResponseEntity.ok(businessService.updateBusiness(businessDTO));
    }
    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteBusiness(@PathVariable Long id){
        businessService.deleteBusiness(id);
        return new ResponseEntity<String>("Deleted successful", HttpStatus.OK);
    }
}
