package com.hillel.evo.adviser.service.impl;

import com.hillel.evo.adviser.dto.BusinessDto;
import com.hillel.evo.adviser.entity.Business;
import com.hillel.evo.adviser.entity.BusinessUser;
import com.hillel.evo.adviser.exception.ResourceNotFoundException;
import com.hillel.evo.adviser.mapper.BusinessMapper;
import com.hillel.evo.adviser.repository.BusinessRepository;
import com.hillel.evo.adviser.repository.BusinessUserRepository;
import com.hillel.evo.adviser.search.CustomSearch;
import com.hillel.evo.adviser.search.QueryFactory;
import com.hillel.evo.adviser.service.BusinessService;
import com.hillel.evo.adviser.service.SearchHelperService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BusinessServiceImpl implements BusinessService {

    private transient final BusinessMapper mapper;
    private transient final BusinessRepository businessRepository;
    private transient final BusinessUserRepository userRepository;
    private transient final CustomSearch<Business> search;
private transient final SearchHelperService searchHelperService;
    public BusinessServiceImpl(BusinessMapper mapper, BusinessRepository businessRepository, BusinessUserRepository userRepository, SearchHelperService searchHelperService,CustomSearch search) {
        this.mapper = mapper;
        this.businessRepository = businessRepository;
        this.userRepository = userRepository;
        this.searchHelperService = searchHelperService;
        this.search = search;
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
    public QueryFactory findBusinessByServiceType(final Class clazz, final String field, final String value){
        return searchHelperService.getTextWildcardQuery(clazz, field, value);

    }
    @Override
    public QueryFactory findBusinessByLocation(Class clazz, double radius, double latitude, double longitude){
        return searchHelperService.getSpatialQuery(clazz,radius,latitude,longitude);
    }

    public List<BusinessDto> findByBusinessTypeServiceTypeLocation( String serviceForBusiness, double longtitude, double latitude ) {
        var serviceForBusinessQuery = searchHelperService.getTextQuery(Business.class,"serviceForBusinnes.name", serviceForBusiness);
        var locationTypeQuery = searchHelperService.getSpatialQuery(Business.class, 12, 12,12);
        var entities = search.search(Business.class, serviceForBusinessQuery, locationTypeQuery);
        List<BusinessDto> result = new ArrayList<>();
        entities.forEach(e -> result.add(mapper.toDto(e)));
        return result;

    }
}