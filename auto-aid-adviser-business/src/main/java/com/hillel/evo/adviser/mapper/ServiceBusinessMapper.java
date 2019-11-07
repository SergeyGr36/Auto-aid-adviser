package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.ServiceBusinessDto;
import com.hillel.evo.adviser.dto.ServiceBusinessShortDto;
import com.hillel.evo.adviser.entity.ServiceBusiness;
import com.hillel.evo.adviser.repository.ServiceBusinessRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ServiceBusinessRepository.class, ServiceTypeMapper.class})
public interface ServiceBusinessMapper {
    ServiceBusiness toEntity(ServiceBusinessDto serviceBusinessDto);
    ServiceBusinessDto toDto(ServiceBusiness businessService);
    List<ServiceBusinessDto> toDto (List<ServiceBusiness> serviceBusinesses);
    ServiceBusinessShortDto toShortDto(ServiceBusiness businessService);

    @Mapping(target = "serviceType", ignore = true)
    ServiceBusiness toEntity(ServiceBusinessShortDto dto);
}
