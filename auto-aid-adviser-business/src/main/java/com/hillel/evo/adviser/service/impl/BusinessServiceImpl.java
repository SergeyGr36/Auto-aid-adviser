package com.hillel.evo.adviser.service.impl;

import com.hillel.evo.adviser.dto.BusinessDto;
import com.hillel.evo.adviser.entity.Business;
import com.hillel.evo.adviser.exception.ResourceNotFoundException;
import com.hillel.evo.adviser.mapper.BusinessMapper;
import com.hillel.evo.adviser.repository.BusinessRepository;
import com.hillel.evo.adviser.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    private BusinessMapper mapper;
    private final BusinessRepository businessRepository;

    public BusinessServiceImpl(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }

    @Override
    public BusinessDto createBusiness(final BusinessDto dto) {
        Business business = mapper.toEntity(dto);
        return mapper.toDto(businessRepository.save(business));
    }

    @Override
    public List<BusinessDto> findAllByUser(Long id) {
        List<Business> businessList = businessRepository.findBusinessesFetchServicesByBusinessUser_Id(id);
        return mapper.listToDto(businessList);
    }

    @Override
    public BusinessDto getBusinessById(Long id) {
        return mapper.toDto(businessRepository.findById(id).orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    public BusinessDto updateBusiness(final BusinessDto dto) {
        return createBusiness(dto);
    }

    @Override
    public void deleteBusiness(Long id) {
        businessRepository.deleteById(id);
    }
}