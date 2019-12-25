package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.BusinessDto;
import com.hillel.evo.adviser.dto.BusinessFullDto;
import com.hillel.evo.adviser.entity.Business;
import com.hillel.evo.adviser.entity.BusinessUser;
import com.hillel.evo.adviser.repository.BusinessUserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {BusinessUserRepository.class,
                ServiceForBusinessMapper.class,
                BusinessTypeMapper.class,
                ServiceTypeMapper.class,
                WorkTimeMapper.class})

public abstract class BusinessMapper {

    public abstract BusinessDto toDto(Business business);

    @Mapping(target = "businessUser", source = "user")
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "contact", source = "dto.contact")
    @Mapping(target = "location", source = "dto.location")
    @Mapping(target = "serviceForBusinesses", source = "dto.serviceForBusinesses")
    @Mapping(target = "workTimes", source = "dto.workTimes")
    @Mapping(target = "images", ignore = true)
    public abstract Business toEntity(BusinessDto dto, BusinessUser user);

    public abstract List<BusinessDto> listToDto(List<Business> businesses);

    public abstract BusinessFullDto toFullDto(Business business);

}