package com.hillel.evo.adviser.controller;

import com.hillel.evo.adviser.dto.BusinessTypeDto;
import com.hillel.evo.adviser.dto.CarBrandDto;
import com.hillel.evo.adviser.dto.CarModelDto;
import com.hillel.evo.adviser.dto.ServiceForBusinessDto;
import com.hillel.evo.adviser.dto.ServiceTypeDto;
import com.hillel.evo.adviser.dto.TypeCarDto;
import com.hillel.evo.adviser.service.BusinessTypeService;
import com.hillel.evo.adviser.service.CarBrandService;
import com.hillel.evo.adviser.service.CarModelService;
import com.hillel.evo.adviser.service.ServiceForBusinessService;
import com.hillel.evo.adviser.service.ServiceTypeService;
import com.hillel.evo.adviser.service.TypeCarService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private final TypeCarService typeCarService;
    private final CarBrandService carBrandService;
    private final CarModelService carModelService;

    public CatalogController(BusinessTypeService businessTypeService, ServiceTypeService serviceTypeService, ServiceForBusinessService serviceForBusinessService, TypeCarService typeCarService, CarBrandService carBrandService, CarModelService carModelService) {
        this.businessTypeService = businessTypeService;
        this.serviceTypeService = serviceTypeService;
        this.serviceForBusinessService = serviceForBusinessService;
        this.typeCarService = typeCarService;
        this.carBrandService = carBrandService;
        this.carModelService = carModelService;
    }

    @GetMapping("/business/types")
    public List<BusinessTypeDto> findAllBusinessType() {
        return businessTypeService.findAll();
    }

    @GetMapping("/business/type/{id}")
    public BusinessTypeDto findBusinessTypeById(@PathVariable Long id) {
        return businessTypeService.findBusinessTypeById(id);
    }

    @GetMapping("/business/type/{id}/service/types")
    public List<ServiceTypeDto> findServiceTypeByBusinessTypeId(@PathVariable Long id) {
        return serviceTypeService.findAllByBusinessTypeId(id);
    }

    //    @Secured(value = {"ROLE_ADMIN", "ROLE_MODERATOR"})
    @PostMapping("/business/type")
    @ResponseStatus(code = HttpStatus.CREATED)
    public BusinessTypeDto saveBusinessType(@RequestBody BusinessTypeDto dto) {
        return businessTypeService.createBusinessType(dto);
    }

    //    @Secured(value = {"ROLE_ADMIN", "ROLE_MODERATOR"})
    @PutMapping("/business/type")
    @ResponseStatus(code = HttpStatus.CREATED)
    public BusinessTypeDto updateBusinessType(@RequestBody BusinessTypeDto dto) {
        return businessTypeService.updateBusinessType(dto);
    }

    //    @Secured(value = {"ROLE_ADMIN", "ROLE_MODERATOR"})
    @DeleteMapping("/business/type/{id}")
    public void deleteBusinessType(@PathVariable Long id) {
        businessTypeService.deleteBusinessType(id);
    }


    @GetMapping("/service/type/{id}")
    public ServiceTypeDto findServiceTypeById(@PathVariable Long id) {
        return serviceTypeService.getServiceTypeById(id);
    }

    @GetMapping("/service/type/{id}/services")
    public List<ServiceForBusinessDto> findServiceByServiceTypeId(@PathVariable Long id) {
        return serviceForBusinessService.getAllByServiceTypeId(id);
    }

    //    @Secured(value = {"ROLE_ADMIN", "ROLE_MODERATOR"})
    @PostMapping("/service/type")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ServiceTypeDto saveServiceType(@RequestBody ServiceTypeDto dto) {
        return serviceTypeService.createServiceType(dto);
    }

    //    @Secured(value = {"ROLE_ADMIN", "ROLE_MODERATOR"})
    @PutMapping("/service/type")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ServiceTypeDto updateServiceType(@RequestBody ServiceTypeDto dto) {
        return serviceTypeService.updateServiceType(dto);
    }

    //    @Secured(value = {"ROLE_ADMIN", "ROLE_MODERATOR"})
    @DeleteMapping("/service/type/{id}")
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

    /* ********* Type Car ********** */

    @GetMapping("/car/types")
    public List<TypeCarDto> getAllTypeCar() {
        return typeCarService.findAll();
    }

    @GetMapping("car/type/{id}")
    public TypeCarDto getTypeCarById(@PathVariable("id") Long typeId) {
        return typeCarService.findById(typeId);
    }

    @GetMapping("/car/type/name/{name}")
    public TypeCarDto getTypeCarByName(@PathVariable("name") String name) {
        return typeCarService.findByName(name);
    }

    /* ********* Car Brand ********** */

    @GetMapping("/car/brands")
    public List<CarBrandDto> getAllCarBrand() {
        return carBrandService.findAll();
    }

    @GetMapping("/car/brand/{id}")
    public CarBrandDto getCarBrandById(@PathVariable("id") Long brandId) {
        return carBrandService.findById(brandId);
    }

    @GetMapping("/car/brand/name/{name}")
    public CarBrandDto getCarBrandByName(@PathVariable("name") String name) {
        return carBrandService.findByName(name);
    }

    /* ********* Car Model ********** */

    @GetMapping("car/model/{id}")
    public CarModelDto getCarModelById(@PathVariable("id") Long modelId) {
        return carModelService.findById(modelId);
    }

    @GetMapping("car/model/type/{typeId}/brand/{brandId}")
    public List<CarModelDto> getCarModelByPathVariable(
            @PathVariable(name = "typeId") Long typeId,
            @PathVariable(name = "brandId") Long brandId
    ) {
            List<CarModelDto> listModel = carModelService.findByTypeAndBrand(typeId, brandId);
            return listModel;
    }

    @GetMapping("car/models")
    public ResponseEntity<List<CarModelDto>> getCarModelByRequestParam(
            @RequestParam(name = "typeName") String typeName,
            @RequestParam(name = "brandName") String brandName
    ) {
        List<CarModelDto> listModel = carModelService.findByTypeAndBrand(typeName, brandName);
        return ResponseEntity.ok(listModel);
    }


}
