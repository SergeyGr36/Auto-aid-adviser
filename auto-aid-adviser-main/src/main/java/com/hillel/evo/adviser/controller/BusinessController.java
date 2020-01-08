package com.hillel.evo.adviser.controller;


import com.hillel.evo.adviser.dto.BusinessDto;
import com.hillel.evo.adviser.dto.BusinessFullDto;
import com.hillel.evo.adviser.dto.FeedbackDto;
import com.hillel.evo.adviser.dto.ImageDto;
import com.hillel.evo.adviser.dto.ServiceForBusinessDto;
import com.hillel.evo.adviser.entity.Feedback;
import com.hillel.evo.adviser.service.BusinessService;
import com.hillel.evo.adviser.service.FeedbackService;
import com.hillel.evo.adviser.service.SecurityUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/businesses")
public class BusinessController {

    private transient final String ROLE_BUSINESS = "ROLE_BUSINESS";

    private transient final String ROLE_USER = "ROLE_USER";

    private transient final BusinessService businessService;

    private transient final FeedbackService feedbackService;

    @Autowired
    public BusinessController(BusinessService businessService, FeedbackService feedbackService) {
        this.businessService = businessService;
        this.feedbackService = feedbackService;
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
        if(businessService.deleteImage(userId, businessId, imageId)) {
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

    /*============ Feedback ==============*/

    @GetMapping("/{id}/feedbacks")
    public Page<FeedbackDto> findFeedbackByBusiness(
            @PathVariable(name = "id") Long businessId,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        return feedbackService.findFeedbackByBusiness(businessId, page, size);
    }

    @GetMapping("/feedback/{feedbackId}")
    public FeedbackDto findFeedbackById(
            @PathVariable Long feedbackId) {
        return feedbackService.findFeedback(feedbackId);
    }

    @Secured(ROLE_USER)
    @PostMapping("/{businessId}/feedback")
    @ResponseStatus(HttpStatus.CREATED)
    public FeedbackDto saveFeedback(@PathVariable("businessId") Long businessId,
                                    Authentication authentication,
                                    @RequestBody FeedbackDto dto) {
        SecurityUserDetails userDetails = (SecurityUserDetails) authentication.getPrincipal();
        return feedbackService.saveFeedback(dto, businessId, userDetails.getUserId());
    }

    @Secured(ROLE_USER)
    @PutMapping("/{businessId}/feedback")
    @ResponseStatus(HttpStatus.CREATED)
    public FeedbackDto updateFeedback(@PathVariable("businessId") Long businessId,
                                    Authentication authentication,
                                    @RequestBody FeedbackDto dto) {
        SecurityUserDetails userDetails = (SecurityUserDetails) authentication.getPrincipal();
        return feedbackService.updateFeedback(dto, businessId, userDetails.getUserId());
    }

}
