package com.hillel.evo.adviser.service.impl;

import com.hillel.evo.adviser.dto.BusinessDto;
import com.hillel.evo.adviser.dto.ServiceForBusinessDto;
import com.hillel.evo.adviser.entity.Business;
import com.hillel.evo.adviser.entity.BusinessUser;
import com.hillel.evo.adviser.exception.ResourceNotFoundException;
import com.hillel.evo.adviser.mapper.BusinessMapper;
import com.hillel.evo.adviser.mapper.ServiceForBusinessMapper;
import com.hillel.evo.adviser.repository.BusinessRepository;
import com.hillel.evo.adviser.repository.BusinessUserRepository;
import com.hillel.evo.adviser.repository.ServiceForBusinessRepository;
import com.hillel.evo.adviser.service.BusinessService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessServiceImpl implements BusinessService {

    private transient final BusinessMapper mapper;
    private transient final ServiceForBusinessMapper serviceMapper;
    private transient final BusinessRepository businessRepository;
    private transient final BusinessUserRepository userRepository;
    private transient final ServiceForBusinessRepository serviceForBusinessRepository;

    public BusinessServiceImpl(BusinessMapper mapper, ServiceForBusinessMapper serviceMapper, BusinessRepository businessRepository, BusinessUserRepository userRepository, ServiceForBusinessRepository serviceForBusinessRepository) {
        this.mapper = mapper;
        this.serviceMapper = serviceMapper;
        this.businessRepository = businessRepository;
        this.userRepository = userRepository;
        this.serviceForBusinessRepository = serviceForBusinessRepository;
    }

    @Override
    public BusinessDto createBusiness(final BusinessDto dto, Long userId) {
        BusinessUser user = userRepository.getOne(userId);
        Business business = mapper.toEntity(dto, user);
        return mapper.toDto(businessRepository.save(business));
    }

    @Override
    public List<BusinessDto> findAllByUser(Long userId) {
        return mapper.listToDto(businessRepository.findBusinessesUserId(userId));
    }

    @Override
    public BusinessDto findBusinessById(Long id, Long userId) {
        return mapper.toDto(businessRepository.findByIdAndBusinessUserId(id, userId)
                     .orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    public BusinessDto updateBusiness(final BusinessDto dto, Long userId) {
        return createBusiness(dto, userId);
    }

    @Override
    public void deleteBusiness(Long id, Long userId) {
        businessRepository.delete(businessRepository
                            .findByIdAndBusinessUserId(id, userId)
                            .orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    public List<ServiceForBusinessDto> findServicesByBusinessId(Long businessId, Long userId) {
        return serviceMapper.toDto(serviceForBusinessRepository.findServicesByBusiness_IdAndBusinessUser_Id(businessId, userId));
    }
}