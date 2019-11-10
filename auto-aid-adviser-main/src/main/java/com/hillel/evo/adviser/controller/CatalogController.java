package com.hillel.evo.adviser.controller;

import com.hillel.evo.adviser.dto.BusinessTypeDto;
import com.hillel.evo.adviser.dto.ServiceBusinessDto;
import com.hillel.evo.adviser.dto.ServiceTypeDto;
import com.hillel.evo.adviser.service.BusinessTypeService;
import com.hillel.evo.adviser.service.ServiceBusinessService;
import com.hillel.evo.adviser.service.ServiceTypeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    private transient final BusinessTypeService businessTypeService;
    private transient final ServiceTypeService serviceTypeService;
    private transient final ServiceBusinessService serviceBusinessService;

    public CatalogController(BusinessTypeService businessTypeService, ServiceTypeService serviceTypeService, ServiceBusinessService serviceBusinessService) {
        this.businessTypeService = businessTypeService;
        this.serviceTypeService = serviceTypeService;
        this.serviceBusinessService = serviceBusinessService;
    }

    @GetMapping("/business_types")
    public List<BusinessTypeDto> findAllBusinessType() {
        return businessTypeService.findAll();
    }

    @GetMapping("/business_types/{id}")
    public BusinessTypeDto findBusinessTypeById(@PathVariable Long id) {
        return businessTypeService.findBusinessTypeById(id);
    }

    @GetMapping("/business_types/{id}/service_types")
    public List<ServiceTypeDto> findServiceTypeByBusinessTypeId(@PathVariable Long id) {
        return serviceTypeService.findAllByBusinessTypeId(id);
    }

    @GetMapping("/service_types/{id}")
    public ServiceTypeDto findServiceTypeById(@PathVariable Long id) {
        return serviceTypeService.getServiceTypeById(id);
    }

    @GetMapping("/service_types/{id}/services")
    public List<ServiceBusinessDto> findServiceByServiceTypeId(@PathVariable Long id) {
        return serviceBusinessService.getAllByServiceTypeId(id);
    }

    @GetMapping("/services")
    public List<ServiceBusinessDto> findAllService() {
        return serviceBusinessService.findAll();
    }

    @GetMapping("/services/{id}")
    public ServiceBusinessDto findServiceById(@PathVariable Long id) {
        return serviceBusinessService.getServiceBusinessById(id);
    }

    @PostMapping("/services")
    public ServiceBusinessDto saveService(@Validated @RequestBody ServiceBusinessDto dto) {
        return serviceBusinessService.createServiceBusiness(dto);
    }

    @PutMapping("/services")
    public ServiceBusinessDto updateService(@Validated @RequestBody ServiceBusinessDto dto) {
        return serviceBusinessService.updateServiceBusiness(dto);
    }
}
