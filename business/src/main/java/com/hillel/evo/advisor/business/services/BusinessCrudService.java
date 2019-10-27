package com.hillel.evo.advisor.business.services;

import com.hillel.evo.advisor.business.dto.BusinessDTO;
import com.hillel.evo.advisor.business.repository.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessCrudService{
    @Autowired
    private BusinessRepository businessRepository;
    private BusinessDTO businessDTO;
public BusinessDTO getBusiness(){
//    --------------------
    return null;
}
}
