package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.ServiceTypeDto;
import com.hillel.evo.adviser.entity.ServiceType;
import com.hillel.evo.adviser.repository.ServiceTypeRepository;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {BusinessTypeMapper.class, ServiceTypeRepository.class})
public interface ServiceTypeMapper {
    ServiceType toEntity(ServiceTypeDto dto);
    ServiceTypeDto toDto(ServiceType type);
    List<ServiceTypeDto> toDto(List<ServiceType> type);
    List<ServiceType> toEntity(List<ServiceTypeDto> dto);

}
