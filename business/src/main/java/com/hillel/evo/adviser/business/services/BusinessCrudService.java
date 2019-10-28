package com.hillel.evo.adviser.business.services;

import com.hillel.evo.adviser.business.dto.BusinessDTO;
import com.hillel.evo.adviser.business.entity.Business;
import com.hillel.evo.adviser.business.entity.BusinessUser;
import com.hillel.evo.adviser.business.repository.BusinessRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
//todo завтра треба розібратись з цим говнокодом)
@Service
public class BusinessCrudService {
    private BusinessRepository businessRepository;

    public BusinessDTO createBusiness(final BusinessDTO dto) {

        return mappedEntityToDto(businessRepository.save(mappedDtoToEntity(dto)));
    }

    public List<BusinessDTO> getAllByOwner(final BusinessUser businessUser) {
        return new ArrayList<>(mappedEntityToDto(businessRepository.findAllById(businessUser.getId)));
    }

    private Business mappedDtoToEntity(final BusinessDTO dto) {
        Business business = new Business();
        BeanUtils.copyProperties(dto, business);
        return business;
    }

    private List<BusinessDTO> mappedEntityToDto(final List<Business> businesses) {

        final List<BusinessDTO> businessDTO = new ArrayList<>();
        BeanUtils.copyProperties(businesses, businessDTO);
        return businessDTO;
    }
    private BusinessDTO mappedEntityToDto (final Business business){
        BusinessDTO businessDTO = new BusinessDTO();
        BeanUtils.copyProperties(business, businessDTO);
        return businessDTO;
    }
}
