package com.hillel.evo.adviser.service.impl;

import com.hillel.evo.adviser.dto.BusinessTypeDto;
import com.hillel.evo.adviser.entity.BusinessType;
import com.hillel.evo.adviser.exception.DeleteException;
import com.hillel.evo.adviser.exception.ResourceNotFoundException;
import com.hillel.evo.adviser.mapper.BusinessTypeMapper;
import com.hillel.evo.adviser.repository.BusinessTypeRepository;
import com.hillel.evo.adviser.search.TextSearch;
import com.hillel.evo.adviser.service.BusinessTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessTypeServiceImpl implements BusinessTypeService {

    private transient final BusinessTypeMapper mapper;
    private transient final BusinessTypeRepository repository;
    private transient final TextSearch<BusinessType> textSearch;

    public BusinessTypeServiceImpl(BusinessTypeMapper mapper, BusinessTypeRepository repository, TextSearch<BusinessType> textSearch) {
        this.mapper = mapper;
        this.repository = repository;
        this.textSearch = textSearch;
    }

    @Override
    public BusinessTypeDto createBusinessType(BusinessTypeDto dto) {
        return mapper.toDto(repository.save(mapper.toType(dto)));
    }

    @Override
    public List<BusinessTypeDto> findAll() {
        return mapper.toAllDto(repository.findAll());
    }

    @Override
    public List<BusinessTypeDto> findAllByName(String name) {
        return mapper.toAllDto(textSearch.search(BusinessType.class, "name", name));
    }

    @Override
    public List<BusinessTypeDto> findAllByNameContains(String name) {
        return mapper.toAllDto(textSearch.searchWildcard(BusinessType.class, "name", name));
    }

    @Override
    public BusinessTypeDto findBusinessTypeById(Long id) {
        return mapper.toDto(repository.findById(id).orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    public BusinessTypeDto updateBusinessType(BusinessTypeDto dto) {
        return createBusinessType(dto);
    }

    @Override
    public void deleteBusinessType(Long id) {
        try {
            repository.deleteById(id);
        } catch (RuntimeException e){
            throw new DeleteException("Delete failed");
        }
    }
}
