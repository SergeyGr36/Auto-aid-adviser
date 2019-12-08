package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.HistoryPointDto;
import com.hillel.evo.adviser.entity.HistoryPoint;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SearchHistoryMapper {
    HistoryPointDto toDto(HistoryPoint historyPoint);

    HistoryPoint toEntity(HistoryPointDto historyPointDto);

    List<HistoryPointDto> toDtoList(List<HistoryPoint> historyPointEntityList);
}