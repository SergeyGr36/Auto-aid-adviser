package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.BusinessDto;
import com.hillel.evo.adviser.entity.Business;
import com.hillel.evo.adviser.repository.BusinessUserRepository;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {BusinessUserRepository.class,
                ServiceBusinessMapper.class,
                BusinessTypeMapper.class,
                ServiceTypeMapper.class})
public interface BusinessMapper {

    @Mapping(target = "userId", source = "businessUser.id")
    BusinessDto toDto(Business business);

    @Mapping(target = "businessUser", source = "dto.userId")
    Business toEntity(BusinessDto dto);

    @InheritConfiguration(name = "toDto")
    List<BusinessDto> listToDto(List<Business> businesses);
}