package com.hillel.evo.adviser.controller;


import com.hillel.evo.adviser.dto.BusinessDto;
import com.hillel.evo.adviser.dto.ImageDto;
import com.hillel.evo.adviser.dto.ServiceForBusinessDto;
import com.hillel.evo.adviser.service.BusinessService;
import com.hillel.evo.adviser.service.SecurityUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/businesses")
public class BusinessController {

    private transient final String ROLE_BUSINESS = "ROLE_BUSINESS";

    private transient final BusinessService businessService;

    @Autowired
    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @Secured(ROLE_BUSINESS)
    @GetMapping
    public ResponseEntity<List<BusinessDto>> getBusiness(Authentication authentication){
        Long userId = getUserFromAuthentication(authentication);
        return new ResponseEntity<>(businessService.findAllByUser(userId), HttpStatus.OK);
    }

    @Secured(ROLE_BUSINESS)
    @GetMapping("/{id}")
    public ResponseEntity<BusinessDto> findBusinessById(@PathVariable Long id, Authentication authentication){
        Long userId = getUserFromAuthentication(authentication);
        return ResponseEntity.ok(businessService.findBusinessById(id, userId));
    }

    @Secured(ROLE_BUSINESS)
    @GetMapping("/{id}/services")
    public List<ServiceForBusinessDto> findServiceByBusinessId(@PathVariable Long id, Authentication authentication){
        Long userId = getUserFromAuthentication(authentication);
        return businessService.findServicesByBusinessId(id, userId);
    }

    @Secured(ROLE_BUSINESS)
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public BusinessDto createBusiness(@Validated @RequestBody final BusinessDto businessDTO, Authentication authentication){
        Long userId = getUserFromAuthentication(authentication);
        return businessService.createBusiness(businessDTO, userId);
    }

    @Secured(ROLE_BUSINESS)
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
                 produces = {MediaType.APPLICATION_JSON_VALUE})
    public BusinessDto createBusiness(@RequestPart(name = "json") @Validated final BusinessDto businessDTO,
                                      @RequestPart(name = "files") List<MultipartFile> files,
                                      Authentication authentication) {
        Long userId = getUserFromAuthentication(authentication);
        return businessService.createBusiness(businessDTO, userId, files);
    }

    @Secured(ROLE_BUSINESS)
    @ResponseStatus(code = HttpStatus.CREATED)
    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public BusinessDto updateBusiness(@Validated @RequestBody final BusinessDto businessDTO, Authentication authentication){
        Long userId = getUserFromAuthentication(authentication);
        return businessService.updateBusiness(businessDTO, userId);
    }

    @Secured(ROLE_BUSINESS)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBusiness(@PathVariable Long id, Authentication authentication){
        Long userId = getUserFromAuthentication(authentication);
        businessService.deleteBusiness(id, userId);
        return new ResponseEntity<String>("Deleted successful", HttpStatus.OK);
    }

    //==== Images ====

    @Secured(ROLE_BUSINESS)
    @GetMapping("/{id}/images")
    public List<ImageDto> findImagesByBusinessId(@PathVariable Long id){
        return businessService.findImagesByBusinessId(id);
    }

    @Secured(ROLE_BUSINESS)
    @PostMapping("/{id}/images")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ImageDto addImageToBusiness(@PathVariable("id") Long businessId,
                                       @RequestPart("file") MultipartFile file,
                                       Authentication authentication) {
        Long userId = getUserFromAuthentication(authentication);
        return businessService.addImage(userId, businessId, file);
    }

    @Secured(ROLE_BUSINESS)
    @DeleteMapping("/{id}/images")
    public ResponseEntity<String> deleteImageFromBusiness(@PathVariable("id") Long businessId,
                                                          @RequestBody @Validated ImageDto dto,
                                                          Authentication authentication) {
        Long userId = getUserFromAuthentication(authentication);
        if(businessService.deleteImage(userId, businessId, dto)) {
            return new ResponseEntity<>("The deleted image is successful", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to delete image", HttpStatus.BAD_REQUEST);
        }
    }

    private Long getUserFromAuthentication(Authentication authentication) {
        SecurityUserDetails userDetails = (SecurityUserDetails) authentication.getPrincipal();
        return userDetails.getUserId();
    }
}
