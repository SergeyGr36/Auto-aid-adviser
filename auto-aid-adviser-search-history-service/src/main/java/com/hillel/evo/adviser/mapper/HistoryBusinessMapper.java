package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.BusinessShortDto;
import com.hillel.evo.adviser.entity.Business;
import com.hillel.evo.adviser.repository.BusinessUserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {BusinessMapper.class,
                BusinessUserRepository.class,
                ServiceForBusinessMapper.class,
                BusinessTypeMapper.class,
                ServiceTypeMapper.class,
                WorkTimeMapper.class})
public interface HistoryBusinessMapper {

    @Mapping(target = "workTimes", ignore = true)
    @Mapping(target = "businessUser", ignore = true)
    @Mapping(target = "serviceForBusinesses", ignore = true)
    @Mapping(target = "images", ignore = true)
    Business toBusiness(BusinessShortDto shortDto);

    BusinessShortDto toBusinessShortDto(Business business);

    List<Business> toBusinessList(List<BusinessShortDto> businessShortDtoList);

    List<BusinessShortDto> toBusinessShortDtoList(List<Business> businesses);
}
