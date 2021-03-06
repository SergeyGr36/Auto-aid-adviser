package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.ServiceForBusinessDto;
import com.hillel.evo.adviser.dto.ServiceForBusinessShortDto;
import com.hillel.evo.adviser.entity.ServiceForBusiness;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {ServiceTypeMapper.class})
public interface ServiceForBusinessMapper extends BaseMapper<ServiceForBusinessDto, ServiceForBusiness> {

    ServiceForBusiness toEntity(ServiceForBusinessDto serviceForBusinessDto);
    ServiceForBusinessDto toDto(ServiceForBusiness businessService);
    Set<ServiceForBusinessDto> toDto (Set<ServiceForBusiness> serviceForBusinesses);
    Set<ServiceForBusiness> toEntity (Set<ServiceForBusinessDto> serviceForBusinessDtos);
    @Override
    List<ServiceForBusinessDto> toDtoList(List<ServiceForBusiness> serviceForBusinesses);
    List<ServiceForBusiness> toEntity (List<ServiceForBusinessDto> serviceForBusinessDtos);
    ServiceForBusinessShortDto toShortDto(ServiceForBusiness serviceForBusinessService);

    @Mapping(target = "serviceType", ignore = true)
    @Mapping(target = "name", ignore = true)
    ServiceForBusiness toEntity(ServiceForBusinessShortDto dto);

}
