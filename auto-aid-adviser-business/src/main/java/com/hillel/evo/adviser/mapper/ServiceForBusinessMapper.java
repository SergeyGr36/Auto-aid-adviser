package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.ServiceForBusinessDto;
import com.hillel.evo.adviser.dto.ServiceForBusinessShortDto;
import com.hillel.evo.adviser.entity.ServiceForBusiness;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ServiceTypeMapper.class})
public interface ServiceForBusinessMapper {
    ServiceForBusiness toEntity(ServiceForBusinessDto serviceForBusinessDto);
    ServiceForBusinessDto toDto(ServiceForBusiness businessService);
    List<ServiceForBusinessDto> toDto (List<ServiceForBusiness> serviceForBusinesses);
    List<ServiceForBusiness> toEntity (List<ServiceForBusinessDto> serviceForBusinessDtos);
    ServiceForBusinessShortDto toShortDto(ServiceForBusiness serviceForBusinessService);

    @Mapping(target = "serviceType", ignore = true)
    ServiceForBusiness toEntity(ServiceForBusinessShortDto dto);

}
