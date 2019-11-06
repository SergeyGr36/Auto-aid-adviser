package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.ServiceTypeDto;
import com.hillel.evo.adviser.entity.ServiceType;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ServiceTypeMapper {
    ServiceType toType(ServiceTypeDto dto);
    ServiceTypeDto toDto(ServiceType type);
    List <ServiceTypeDto> allToDto(List<ServiceType> type);
}
