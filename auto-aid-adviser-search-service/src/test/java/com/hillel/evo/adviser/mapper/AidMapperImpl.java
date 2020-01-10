package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.AidDto;
import com.hillel.evo.adviser.entity.Aid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AidMapperImpl implements AidMapper {

    @Override
    public List<AidDto> toDtoList(List<Aid> entities) {
        return entities.stream()
                .map(e -> new AidDto(e.getName(), e.getType(), e.getLatitude(), e.getLongitude()))
                .collect(Collectors.toList());
    }
}
