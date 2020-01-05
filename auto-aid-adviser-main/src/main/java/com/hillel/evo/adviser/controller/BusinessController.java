package com.hillel.evo.adviser.controller;


import com.hillel.evo.adviser.dto.BusinessDto;
import com.hillel.evo.adviser.dto.BusinessFullDto;
import com.hillel.evo.adviser.dto.ImageDto;
import com.hillel.evo.adviser.dto.ServiceForBusinessDto;
import com.hillel.evo.adviser.service.BusinessService;
import com.hillel.evo.adviser.service.SecurityUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/businesses")
@RequiredArgsConstructor
public class BusinessController {

    private transient final String ROLE_BUSINESS = "ROLE_BUSINESS";

    private transient final BusinessService businessService;

    @Secured(ROLE_BUSINESS)
    @GetMapping
    public ResponseEntity<List<BusinessDto>> getBusiness(Authentication authentication) {
        Long userId = getUserFromAuthentication(authentication);
        return new ResponseEntity<>(businessService.findAllByUser(userId), HttpStatus.OK);
    }

    @Secured(ROLE_BUSINESS)
    @GetMapping("/{id}")
    public ResponseEntity<BusinessDto> findBusinessById(@PathVariable Long id, Authentication authentication) {
        Long userId = getUserFromAuthentication(authentication);
        return ResponseEntity.ok(businessService.findBusinessById(id, userId));
    }

    @Secured(ROLE_BUSINESS)
    @GetMapping("/{id}/services")
    public List<ServiceForBusinessDto> findServiceByBusinessId(@PathVariable Long id, Authentication authentication) {
        Long userId = getUserFromAuthentication(authentication);
        return businessService.findServicesByBusinessId(id, userId);
    }

    @Secured(ROLE_BUSINESS)
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public BusinessDto createBusiness(@Validated @RequestBody final BusinessDto businessDTO, Authentication authentication) {
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
    public BusinessDto updateBusiness(@Validated @RequestBody final BusinessDto businessDTO, Authentication authentication) {
        Long userId = getUserFromAuthentication(authentication);
        return businessService.updateBusiness(businessDTO, userId);
    }

    @Secured(ROLE_BUSINESS)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBusiness(@PathVariable Long id, Authentication authentication) {
        Long userId = getUserFromAuthentication(authentication);
        businessService.deleteBusiness(id, userId);
        return new ResponseEntity<String>("Deleted successful", HttpStatus.OK);
    }

    //==== Images ====

    @Secured(ROLE_BUSINESS)
    @GetMapping("/{id}/images")
    public List<ImageDto> findImagesByBusinessId(@PathVariable Long id) {
        return businessService.findImagesByBusinessId(id);
    }

    @Secured(ROLE_BUSINESS)
    @PostMapping("/{id}/images")
    @ResponseStatus(code = HttpStatus.CREATED)
    public List<ImageDto> addImageToBusiness(@PathVariable("id") Long businessId,
                                             @RequestPart("files") List<MultipartFile> files,
                                             Authentication authentication) {
        Long userId = getUserFromAuthentication(authentication);
        return businessService.addImages(userId, businessId, files);
    }

    @Secured(ROLE_BUSINESS)
    @DeleteMapping("/{businessId}/images/{imageId}")
    public ResponseEntity<String> deleteImageFromBusiness(@PathVariable("businessId") Long businessId,
                                                          @PathVariable("imageId") Long imageId,
                                                          Authentication authentication) {
        Long userId = getUserFromAuthentication(authentication);
        if (businessService.deleteImage(userId, businessId, imageId)) {
            return new ResponseEntity<>("The deleted image is successful", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to delete image", HttpStatus.BAD_REQUEST);
        }
    }

    @Secured(ROLE_BUSINESS)
    @GetMapping("/templates")
    public BusinessFullDto getTemplateBusiness() {
        return businessService.createTemplateBusiness();
    }

    private Long getUserFromAuthentication(Authentication authentication) {
        SecurityUserDetails userDetails = (SecurityUserDetails) authentication.getPrincipal();
        return userDetails.getUserId();
    }


    @Secured(ROLE_BUSINESS)
    @GetMapping("/{serviceForBusiness}/{longtitude}/{latitude}")
    public List<BusinessDto> findByBusinessTypeServiceTypeLocation(@PathVariable String serviceForBusiness, @PathVariable double longtitude, @PathVariable double latitude) {
        return businessService.findBusinessByTypeAndLocation(serviceForBusiness, longtitude, latitude);
    }
}
