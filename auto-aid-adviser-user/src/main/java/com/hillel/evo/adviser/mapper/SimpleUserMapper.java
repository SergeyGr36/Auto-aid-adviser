package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.SimpleUserDto;
import com.hillel.evo.adviser.entity.SimpleUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = AdviserUserDetailsMapper.class)
public interface SimpleUserMapper {

    SimpleUser toEntity (SimpleUserDto simpleUserDto);

    SimpleUserDto toDto (SimpleUser simpleUser);
}
