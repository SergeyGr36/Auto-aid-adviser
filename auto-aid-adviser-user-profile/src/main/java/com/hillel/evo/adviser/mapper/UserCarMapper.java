package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.UserCarDto;
import com.hillel.evo.adviser.entity.SimpleUser;
import com.hillel.evo.adviser.entity.UserCar;
import com.hillel.evo.adviser.repository.SimpleUserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CarIdentificationMapper.class, SimpleUserRepository.class})
public interface UserCarMapper {

    @Mapping(target = "simpleUser", source = "user")
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "releaseYear", source = "dto.releaseYear")
    @Mapping(target = "individualCarNaming", source = "dto.individualCarNaming")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "brand", source = "dto.brand")
    @Mapping(target = "motorType", source = "dto.motorType")
    @Mapping(target = "typeCar", source = "dto.typeCar")
    UserCar toCar(UserCarDto dto, SimpleUser user);

    UserCarDto toDto(UserCar car);

    List<UserCarDto> toDtoList(List<UserCar> cars);
}
