package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.entity.SimpleUser;
import com.hillel.evo.adviser.repository.SimpleUserRepository;
import com.hillel.evo.adviser.dto.UserProfileDto;
import com.hillel.evo.adviser.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = SimpleUserRepository.class)
public interface UserProfileMapper {
    @Mapping(target = "simpleUser", source = "user")
    @Mapping(target = "id", source = "dto.id")
//    @Mapping(target = "typeCar", source = "dto.typeCar")
//    @Mapping(target = "motorType", source = "dto.motorType")
//    @Mapping(target = "carBrand", source = "dto.carBrand")
    UserProfile toUser(UserProfileDto dto, SimpleUser user);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "user", source = "simpleUser")
//    @Mapping(target = "typeCar", source = "typeCar")
//    @Mapping(target = "carBrand", source = "carBrand")
    UserProfileDto toDto (UserProfile user);

}
