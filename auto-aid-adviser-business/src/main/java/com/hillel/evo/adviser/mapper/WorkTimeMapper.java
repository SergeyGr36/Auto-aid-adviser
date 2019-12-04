package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.WorkTimeDto;
import com.hillel.evo.adviser.entity.WorkTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface WorkTimeMapper {

    @Mapping(target = "day", expression = "java(DayOfWeek.of(dto.getDay()))")
    WorkTime toEntity(WorkTimeDto dto);

    @Mapping(target = "day", source = "day.value")
    WorkTimeDto toDto(WorkTime entity);

    Set<WorkTime> toListEntity(Set<WorkTimeDto> dtos);

    Set<WorkTimeDto> toListDto(Set<WorkTime> entities);
}
