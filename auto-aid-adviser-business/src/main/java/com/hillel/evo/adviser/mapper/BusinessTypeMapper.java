package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.BusinessTypeDto;
import com.hillel.evo.adviser.entity.BusinessType;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BusinessTypeMapper {
    BusinessType toType(BusinessTypeDto dto);
    BusinessTypeDto toDto(BusinessType type);
    List<BusinessTypeDto> toAllDto (List<BusinessType> type);
    List<BusinessType> toAllType (List<BusinessTypeDto> dto);

}
