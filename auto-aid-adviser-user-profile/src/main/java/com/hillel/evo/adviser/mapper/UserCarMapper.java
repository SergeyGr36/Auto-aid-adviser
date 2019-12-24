package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.UserCarDto;
import com.hillel.evo.adviser.entity.SimpleUser;
import com.hillel.evo.adviser.entity.UserCar;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetType;

import javax.validation.constraints.NotNull;
import java.util.List;

@Mapper(componentModel = "spring", uses = {CarModelMapper.class, ImageMapper.class})
public interface UserCarMapper {

    @Mapping(target = "simpleUser", source = "user")
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "releaseYear", source = "dto.releaseYear")
    @Mapping(target = "individualCarNaming", source = "dto.individualCarNaming")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "carModel", source = "dto.carModel")
    @Mapping(target = "images", ignore = true)
    UserCar toCar(UserCarDto dto, @NotNull SimpleUser user);

    UserCarDto toDto(UserCar car);

    List<UserCarDto> toDtoList(List<UserCar> cars);

    @Mapping(target = "simpleUser", ignore = true)
    @Mapping(target = "images", ignore = true)
    UserCar updateUserCar(UserCarDto carDto, @MappingTarget UserCar car);
}
