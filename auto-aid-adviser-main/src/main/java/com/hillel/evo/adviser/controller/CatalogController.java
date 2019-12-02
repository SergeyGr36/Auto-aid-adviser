package com.hillel.evo.adviser.controller;

import com.hillel.evo.adviser.dto.BusinessTypeDto;
import com.hillel.evo.adviser.dto.ServiceForBusinessDto;
import com.hillel.evo.adviser.dto.ServiceTypeDto;
import com.hillel.evo.adviser.service.BusinessTypeService;
import com.hillel.evo.adviser.service.ServiceForBusinessService;
import com.hillel.evo.adviser.service.ServiceTypeService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import java.util.List;

@RestController
@RequestMapping("/catalog")
@Validated
public class CatalogController {

    private transient final BusinessTypeService businessTypeService;
    private transient final ServiceTypeService serviceTypeService;
    private transient final ServiceForBusinessService serviceForBusinessService;

    public CatalogController(BusinessTypeService businessTypeService, ServiceTypeService serviceTypeService, ServiceForBusinessService serviceForBusinessService) {
        this.businessTypeService = businessTypeService;
        this.serviceTypeService = serviceTypeService;
        this.serviceForBusinessService = serviceForBusinessService;
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

    //    @Secured(value = {"ROLE_ADMIN", "ROLE_MODERATOR"})
    @PostMapping("/business_types")
    @ResponseStatus(code = HttpStatus.CREATED)
    public BusinessTypeDto saveBusinessType(@RequestBody BusinessTypeDto dto) {
        return businessTypeService.createBusinessType(dto);
    }

    //    @Secured(value = {"ROLE_ADMIN", "ROLE_MODERATOR"})
    @PutMapping("/business_types")
    @ResponseStatus(code = HttpStatus.CREATED)
    public BusinessTypeDto updateBusinessType(@RequestBody BusinessTypeDto dto) {
        return businessTypeService.updateBusinessType(dto);
    }

    //    @Secured(value = {"ROLE_ADMIN", "ROLE_MODERATOR"})
    @DeleteMapping("/business_types/{id}")
    public void deleteBusinessType(@PathVariable Long id) {
        businessTypeService.deleteBusinessType(id);
    }


    @GetMapping("/service_types/{id}")
    public ServiceTypeDto findServiceTypeById(@PathVariable Long id) {
        return serviceTypeService.getServiceTypeById(id);
    }

    @GetMapping("/service_types/{id}/services")
    public List<ServiceForBusinessDto> findServiceByServiceTypeId(@PathVariable Long id) {
        return serviceForBusinessService.getAllByServiceTypeId(id);
    }

    //    @Secured(value = {"ROLE_ADMIN", "ROLE_MODERATOR"})
    @PostMapping("/service_types")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ServiceTypeDto saveServiceType(@RequestBody ServiceTypeDto dto) {
        return serviceTypeService.createServiceType(dto);
    }

    //    @Secured(value = {"ROLE_ADMIN", "ROLE_MODERATOR"})
    @PutMapping("/service_types")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ServiceTypeDto updateServiceType(@RequestBody ServiceTypeDto dto) {
        return serviceTypeService.updateServiceType(dto);
    }

    //    @Secured(value = {"ROLE_ADMIN", "ROLE_MODERATOR"})
    @DeleteMapping("/service_types/{id}")
    public void deleteServiceType(@PathVariable Long id) {
        serviceTypeService.deleteServiceType(id);
    }

    @GetMapping("/services")
    public Page<ServiceForBusinessDto> servicesByPages(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "50") @Max(100) Integer size) {
        return serviceForBusinessService.byPages(page, size);
    }

    @GetMapping("/services/{id}")
    public ServiceForBusinessDto findServiceById(@PathVariable Long id) {
        return serviceForBusinessService.getServiceBusinessById(id);
    }

    @PostMapping("/services")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ServiceForBusinessDto saveService(@RequestBody ServiceForBusinessDto dto) {
        return serviceForBusinessService.createServiceBusiness(dto);
    }

    @PutMapping("/services")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ServiceForBusinessDto updateService(@RequestBody ServiceForBusinessDto dto) {
        return serviceForBusinessService.updateServiceBusiness(dto);
    }

}
