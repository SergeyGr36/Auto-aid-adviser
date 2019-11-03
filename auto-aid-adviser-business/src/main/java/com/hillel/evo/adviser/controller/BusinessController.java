package com.hillel.evo.adviser.controller;


import com.hillel.evo.adviser.dto.BusinessDto;
import com.hillel.evo.adviser.service.impl.BusinessServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/business")
public class BusinessController {
    private final BusinessServiceImpl businessService;

    @Autowired
    public BusinessController(BusinessServiceImpl businessService) {
        this.businessService = businessService;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<BusinessDto>> getBusinessByOwner(@PathVariable Long id){
        return new ResponseEntity<>(businessService.findAllByUser(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BusinessDto> createBusiness(@RequestBody final BusinessDto businessDTO){
        return ResponseEntity.ok(businessService.createBusiness(businessDTO));
    }
}
