package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.UserCarDto;
import com.hillel.evo.adviser.entity.SimpleUser;
import com.hillel.evo.adviser.entity.UserCar;
import com.hillel.evo.adviser.repository.SimpleUserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {SimpleUserRepository.class,
        CarIdentificationMapper.class})
public interface UserCarMapper {

    @Mapping(target = "simpleUser", source = "user")
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "brand", source = "dto.brandDto")
    @Mapping(target = "motorType", source = "dto.motorTypeDto")
    @Mapping(target = "typeCar", source = "dto.typeCarDto")
    UserCar toCar(UserCarDto dto, SimpleUser user);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "brandDto", source = "brand")
    @Mapping(target = "motorTypeDto", source = "motorType")
    @Mapping(target = "typeCarDto", source = "typeCar")
    UserCarDto toDto(UserCar car);
    List<UserCarDto> toDtoList(List<UserCar> cars);
}
