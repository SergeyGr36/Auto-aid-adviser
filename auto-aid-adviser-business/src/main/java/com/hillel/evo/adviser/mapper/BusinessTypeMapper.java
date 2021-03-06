package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.BusinessTypeDto;
import com.hillel.evo.adviser.entity.BusinessType;
import com.hillel.evo.adviser.repository.BusinessTypeRepository;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {BusinessTypeRepository.class})
public interface BusinessTypeMapper extends BaseMapper<BusinessTypeDto, BusinessType> {
    BusinessType toType(BusinessTypeDto dto);
    BusinessTypeDto toDto(BusinessType type);
    @Override
    List<BusinessTypeDto> toDtoList(List<BusinessType> type);
    List<BusinessType> toAllType (List<BusinessTypeDto> dto);
}
