package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.AdviserUserDetailsDto;
import com.hillel.evo.adviser.entity.AdviserUserDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdviserUserDetailsMapper {

//    AdviserUserDetails toEntity(AdviserUserDetailsDto adviserUserDetailsDto);

    AdviserUserDetailsDto toDto(AdviserUserDetails adviserUserDetails);
}
