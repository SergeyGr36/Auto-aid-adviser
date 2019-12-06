package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.UserCarDto;
import com.hillel.evo.adviser.entity.SimpleUser;
import com.hillel.evo.adviser.entity.UserCar;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CarIdentificationMapper.class})
public interface UserCarMapper {

    @Mapping(target = "simpleUser", source = "user")
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "brand", source = "dto.brand")
    @Mapping(target = "motorType", source = "dto.motorType")
    @Mapping(target = "typeCar", source = "dto.typeCar")
    UserCar toCar(UserCarDto dto, SimpleUser user);

    UserCarDto toDto(UserCar car);

    List<UserCarDto> toDtoList(List<UserCar> cars);
}
