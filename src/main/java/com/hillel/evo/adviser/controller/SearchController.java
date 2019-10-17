package com.hillel.evo.adviser.controller;

import com.hillel.evo.adviser.search.entity.Aid;
import com.hillel.evo.adviser.search.service.AidSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("search")
public class SearchController {

    private AidSearchService searchService;

    @Autowired
    public void setSearchService(AidSearchService searchService) {

    }

    @GetMapping
    public @ResponseBody
    List<Aid> findAid(
            @RequestParam @NotBlank String serviceType,
            @RequestParam @NotBlank double lat,
            @RequestParam @NotBlank double lon,
            @RequestParam double distance
            ) {


        return searchService.search(serviceType, distance, lat, lon);
    }
}
