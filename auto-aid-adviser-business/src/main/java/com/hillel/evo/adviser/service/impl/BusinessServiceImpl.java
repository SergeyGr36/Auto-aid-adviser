package com.hillel.evo.adviser.service.impl;

import com.hillel.evo.adviser.dto.BusinessDto;
import com.hillel.evo.adviser.entity.Business;
import com.hillel.evo.adviser.mapper.BusinessMapper;
import com.hillel.evo.adviser.repository.BusinessRepository;
import com.hillel.evo.adviser.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    private BusinessMapper mapper;
    private final BusinessRepository businessRepository;

    public BusinessServiceImpl(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
        //mapper = BusinessMapper.INSTANCE;
    }

    @Override
    public BusinessDto createBusiness(final BusinessDto dto) {
        Business business = mapper.toEntity(dto);
        return mapper.toDto(businessRepository.save(business));
    }

    @Override
    public List<BusinessDto> findAllByUser(Long id) {
        return mapper.listToDto(businessRepository.findAllByBusinessUser_Id(id));
    }

    @Override
    public BusinessDto getBusinessById(Long id) {
        return null;
    }

    @Override
    public BusinessDto updateBusiness(BusinessDto dto) {
        return createBusiness(dto);
    }

    @Override
    public void deleteBusiness(Long id) {
        businessRepository.deleteById(id);
    }
}