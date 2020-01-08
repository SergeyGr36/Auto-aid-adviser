package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.FeedbackDto;
import com.hillel.evo.adviser.entity.Business;
import com.hillel.evo.adviser.entity.Feedback;
import com.hillel.evo.adviser.entity.SimpleUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {SimpleUserMapper.class, BusinessMapper.class})
public interface FeedbackMapper {

    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "text", source = "dto.text")
    @Mapping(target = "rating", source = "dto.rating")
    @Mapping(target = "business", source = "business")
    @Mapping(target = "simpleUser", source = "simpleUser")
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "editDate", ignore = true)
    Feedback toEntity(FeedbackDto dto, Business business, SimpleUser simpleUser);

    FeedbackDto toDto(Feedback entity);

    List<FeedbackDto> toListDto(List<Feedback> listEntities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "text")
    @Mapping(target = "rating", source = "rating")
    @Mapping(target = "business", ignore = true)
    @Mapping(target = "simpleUser", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "editDate", ignore = true)
    Feedback updateEntity(FeedbackDto dto, @MappingTarget Feedback entity);
}
