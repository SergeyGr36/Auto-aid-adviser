package com.hillel.evo.adviser.controller;

import com.hillel.evo.adviser.dto.HistoryPointDto;
import com.hillel.evo.adviser.service.CheckHistoryInDatabaseService;
import com.hillel.evo.adviser.service.SearchHistoryService;
import com.hillel.evo.adviser.service.SecurityUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user/history")
@RequiredArgsConstructor
public class SearchHistoryController {
    private final SearchHistoryService searchHistoryService;
    private final CheckHistoryInDatabaseService checkHistoryInDatabaseService;

    @Secured("ROLE_USER")
    @GetMapping
    public ResponseEntity<List<HistoryPointDto>> getPointHistory(
            Authentication authentication) {
        Long userId = getUserFromAuthentication(authentication);
        return new ResponseEntity<>(searchHistoryService.getAllHistory(userId), HttpStatus.OK);
    }

    private Long getUserFromAuthentication(Authentication authentication) {
        SecurityUserDetails userDetails = (SecurityUserDetails) authentication.getPrincipal();
        return userDetails.getUserId();
    }
}

