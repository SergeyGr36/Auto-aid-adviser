package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.dto.BusinessDto;
import com.hillel.evo.adviser.entity.Business;
import com.hillel.evo.adviser.mapper.BusinessMapper;
import com.hillel.evo.adviser.repository.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessServiceImpl implements BusinessService {

    private BusinessMapper mapper;
    private final BusinessRepository businessRepository;

    public BusinessServiceImpl(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
        mapper = BusinessMapper.INSTANCE;
    }

    @Override
    public BusinessDto createBusiness(final BusinessDto dto) {
        Business business = mapper.toEntity(dto);
        return mapper.toDto(businessRepository.save(business));
    }

    @Override
    public List<BusinessDto> findAllByUser(Long id) {
        return null;
    }
}