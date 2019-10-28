package com.hillel.evo.adviser.business.controller;


import com.hillel.evo.adviser.business.dto.BusinessDTO;
import com.hillel.evo.adviser.business.entity.BusinessUser;
import com.hillel.evo.adviser.business.services.BusinessCrudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/business")
public class BusinessController {
    private BusinessCrudService businessCrudService;

    public BusinessController(BusinessCrudService businessCrudService) {
        this.businessCrudService=businessCrudService;
    }
    @GetMapping
    public ResponseEntity<List<BusinessDTO>> getBusinessByOwner(@RequestBody final BusinessUser businessUser){
        return ResponseEntity.ok(businessCrudService.getAllByOwner(businessUser));
    }
    @PostMapping
    public ResponseEntity createBusiness(@RequestBody final BusinessDTO businessDTO){
        return ResponseEntity.ok(businessCrudService.createBusiness(businessDTO));
    }
}
