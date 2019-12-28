package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.SimpleUserDto;
import com.hillel.evo.adviser.entity.SimpleUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SimpleUserMapper {

    SimpleUserDto toDto(SimpleUser simpleUser);

    @Mapping(target = "userDetails", ignore = true)
    SimpleUser toEntity(SimpleUserDto simpleUserDto);
}
