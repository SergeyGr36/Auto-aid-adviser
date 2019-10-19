package com.hillel.evo.adviser.controller;

import com.hillel.evo.adviser.search.entity.Aid;
import com.hillel.evo.adviser.search.service.AidSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("search")
public class SearchController {

    private transient AidSearchService searchService;

    @Autowired
    public void setSearchService(AidSearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Aid> findAid(
            @RequestParam @NotBlank String type,
            @RequestParam @NotBlank Double lat,
            @RequestParam @NotBlank Double lon,
            @RequestParam(required = false) Double distance
            ) {

        if (distance == null) {
            distance = 5.0;
        }

        return searchService.search(type, distance, lat, lon);
    }
}
