package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.BusinessTypeDto;
import com.hillel.evo.adviser.dto.ServiceBusinessDto;
import com.hillel.evo.adviser.entity.BusinessType;
import com.hillel.evo.adviser.entity.ServiceBusiness;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ServiceBusinessMapper {
    ServiceBusiness toService(ServiceBusinessDto serviceBusinessDto);
    ServiceBusinessDto toDto(ServiceBusiness businessService);
    List<ServiceBusinessDto> toAllDto (List<ServiceBusiness> serviceBusinesses);

}
