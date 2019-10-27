package com.hillel.evo.advisor.business.controller;


import com.hillel.evo.advisor.business.services.BusinessCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/business")
public class BusinessController {
    @Autowired
    private BusinessCrudService businessCrudService;

    public BusinessController(BusinessCrudService businessCrudService) {
        this.businessCrudService=businessCrudService;
    }

}
