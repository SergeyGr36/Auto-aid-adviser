package com.hillel.evo.adviser.search.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarSearchService {

    private transient CarSearchService carSearchService;

    @Autowired
    private void setCarSearchService(CarSearchService carSearchService) {
        this.carSearchService = carSearchService;
    }


}
