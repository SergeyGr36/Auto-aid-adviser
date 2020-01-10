package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.AidDto;
import com.hillel.evo.adviser.entity.Aid;

import java.util.List;

public interface AidMapper extends BaseMapper<AidDto, Aid> {

    @Override
    List<AidDto> toDtoList(List<Aid> entities);
}
