package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.HistoryPointDto;
import com.hillel.evo.adviser.entity.HistoryPoint;
import com.hillel.evo.adviser.repository.BusinessUserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {HistoryBusinessMapper.class,
                BusinessUserRepository.class,
                ServiceForBusinessMapper.class,
                BusinessTypeMapper.class,
                ServiceTypeMapper.class,
                WorkTimeMapper.class})
public interface SearchHistoryMapper {
    @Mapping(target = "id", source = "historyPoint.id")
    @Mapping(target = "userId", source = "historyPoint.userId")
    @Mapping(target = "businessDto", source = "historyPoint.business")
    @Mapping(target = "searchDate", source = "historyPoint.searchDate")
    HistoryPointDto toDto(HistoryPoint historyPoint);

    @Mapping(target = "id", source = "historyPointDto.id")
    @Mapping(target = "userId", source = "historyPointDto.userId")
    @Mapping(target = "business", source = "historyPointDto.businessDto")
    @Mapping(target = "searchDate", source = "historyPointDto.searchDate")
    HistoryPoint toEntity(HistoryPointDto historyPointDto);

    List<HistoryPointDto> toDtoList(List<HistoryPoint> historyPointEntityList);
}