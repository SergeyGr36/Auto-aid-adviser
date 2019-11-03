package com.hillel.evo.adviser.controller;


import com.hillel.evo.adviser.dto.BusinessDto;
import com.hillel.evo.adviser.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<BusinessDto>> getBusinessByOwner(@PathVariable Long id){
        return null;//ResponseEntity.ok(businessCrudService.getAllByOwner(id));
    }

    @PostMapping
    public ResponseEntity<BusinessDto> createBusiness(@RequestBody final BusinessDto businessDTO){
        return ResponseEntity.ok(businessService.createBusiness(businessDTO));
    }
}
