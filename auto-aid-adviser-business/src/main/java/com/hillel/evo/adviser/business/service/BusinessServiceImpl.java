package com.hillel.evo.adviser.business.service;

import com.hillel.evo.adviser.business.dto.BusinessDto;
import com.hillel.evo.adviser.business.entity.Business;
import com.hillel.evo.adviser.business.mapper.BusinessMapper;
import com.hillel.evo.adviser.business.mapper.BusinessMapperBU;
import com.hillel.evo.adviser.business.repository.BusinessRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessServiceImpl implements BusinessService {

    //private final BusinessMapperBU mapper = new BusinessMapperBU();
    private final BusinessRepository businessRepository;

    public BusinessServiceImpl(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }

    @Override
    public BusinessDto createBusiness(final BusinessDto dto) {
        //Business business = mapper.toEntity(dto);
        //return mapper.toDto(businessRepository.save(business));
        return null;
    }

    @Override
    public List<BusinessDto> findAllByUser(Long id) {
        return null;
    }
}