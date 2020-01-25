package com.hillel.evo.adviser.service.impl;

import com.hillel.evo.adviser.dto.BusinessTypeDto;
import com.hillel.evo.adviser.dto.SearchTextDTO;
import com.hillel.evo.adviser.entity.BusinessType;
import com.hillel.evo.adviser.exception.DeleteException;
import com.hillel.evo.adviser.exception.ResourceNotFoundException;
import com.hillel.evo.adviser.mapper.BusinessTypeMapper;
import com.hillel.evo.adviser.repository.BusinessTypeRepository;
import com.hillel.evo.adviser.search.TextSearch;
import com.hillel.evo.adviser.service.BusinessTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BusinessTypeServiceImpl implements BusinessTypeService {

    private transient final BusinessTypeMapper mapper;
    private transient final BusinessTypeRepository repository;
    private transient final TextSearch<BusinessTypeDto> textSearch;

    @Override
    public BusinessTypeDto createBusinessType(BusinessTypeDto dto) {
        return mapper.toDto(repository.save(mapper.toType(dto)));
    }

    @Override
    public List<BusinessTypeDto> findAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @Override
    public List<BusinessTypeDto> findAllByName(String name) {
        var dto = new SearchTextDTO(BusinessType.class, "name", name);
        return textSearch.search(mapper, dto);
    }

    @Override
    public List<BusinessTypeDto> findAllByNameContains(String name) {
        var dto = new SearchTextDTO(BusinessType.class, "name", name);
        return textSearch.searchWildcard(mapper, dto);
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
