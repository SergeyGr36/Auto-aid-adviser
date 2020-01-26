package com.hillel.evo.adviser.controller;

import com.hillel.evo.adviser.dto.BusinessDto;
import com.hillel.evo.adviser.service.BusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {

    private transient final BusinessService businessService;

    @GetMapping
    public ResponseEntity<List<BusinessDto>> findByServiceAndLocation(
            @RequestParam String service,
            @RequestParam double longitude,
            @RequestParam double latitude,
            @RequestParam(required = false, defaultValue = "5.0") double radius
    ) {
        var result = businessService.findByServiceAndLocation(service, latitude, longitude, radius);
        if (result.size() > 0) {
            return new ResponseEntity(result, HttpStatus.OK);
        } else {
            return new ResponseEntity(result, HttpStatus.NOT_FOUND);
        }
    }
}
