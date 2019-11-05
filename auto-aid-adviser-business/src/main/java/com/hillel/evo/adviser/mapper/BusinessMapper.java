package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.BusinessDto;
import com.hillel.evo.adviser.entity.Business;
import com.hillel.evo.adviser.entity.ServiceBusiness;
import com.hillel.evo.adviser.repository.BusinessUserRepository;
import com.hillel.evo.adviser.repository.ServiceBusinessRepository;
import com.hillel.evo.adviser.repository.TypeBusinessRepository;
import com.hillel.evo.adviser.service.UserService;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        uses = {BusinessUserRepository.class,
                TypeBusinessRepository.class,
                ServiceBusinessRepository.class})
public interface BusinessMapper {

    @Mapping(target = "userId", source = "businessUser.id")
    @Mapping(target = "serviceBusinessesIds", source = "serviceBusinesses", qualifiedByName = "listSBusinessToListId")
    BusinessDto toDto(Business business);

    @Mapping(target = "businessUser", source = "dto.userId")
    @Mapping(target = "serviceBusinesses", source = "serviceBusinessesIds")
    Business toEntity(BusinessDto dto);

    @InheritConfiguration(name = "toDto")
    List<BusinessDto> listToDto(List<Business> businesses);

    default List<Long> listSBusinessToListId(List<ServiceBusiness> list) {
        return list.stream().map(sb -> sb.getId()).collect(Collectors.toList());
    }
}