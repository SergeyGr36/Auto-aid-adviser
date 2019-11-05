package com.hillel.evo.adviser.service.impl;

import com.hillel.evo.adviser.repository.ServiceBusinessRepository;
import com.hillel.evo.adviser.service.ServiceBusinessService;
import org.springframework.stereotype.Service;

@Service
public class ServiceBusinessServiceImpl implements ServiceBusinessService {

    private final ServiceBusinessRepository serviceBusinessRepository;

    public ServiceBusinessServiceImpl(ServiceBusinessRepository serviceBusinessRepository) {
        this.serviceBusinessRepository = serviceBusinessRepository;
    }

}
