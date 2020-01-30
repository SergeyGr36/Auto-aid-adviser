package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.BusinessDto;
import com.hillel.evo.adviser.dto.BusinessFullDto;
import com.hillel.evo.adviser.dto.BusinessShortDto;
import com.hillel.evo.adviser.entity.Business;
import com.hillel.evo.adviser.entity.BusinessUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {ServiceForBusinessMapper.class,
                BusinessTypeMapper.class,
                ServiceTypeMapper.class,
                WorkTimeMapper.class,
                ImageMapper.class})

public interface BusinessMapper extends BaseMapper<BusinessFullDto, Business> {

    BusinessDto toDto(Business business);

    @Mapping(target = "businessUser", source = "user")
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "contact", source = "dto.contact")
    @Mapping(target = "location", source = "dto.location")
    @Mapping(target = "serviceForBusinesses", source = "dto.serviceForBusinesses")
    @Mapping(target = "workTimes", source = "dto.workTimes")
    @Mapping(target = "images", ignore = true)
    Business toEntity(BusinessDto dto, BusinessUser user);

    @Mapping(target = "workTimes", ignore = true)
    @Mapping(target = "businessUser", ignore = true)
    @Mapping(target = "serviceForBusinesses", ignore = true)
    @Mapping(target = "images", ignore = true)
    Business toEntityShort(BusinessShortDto shortDto);

    BusinessShortDto toShortDto(Business business);

    List<BusinessShortDto> toShortDtoList(List<Business> businesses);

    List<BusinessDto> listToDto(List<Business> businesses);

    BusinessFullDto toFullDto(Business business);

    @Override
    List<BusinessFullDto> toDtoList(List<Business> businesses);

}