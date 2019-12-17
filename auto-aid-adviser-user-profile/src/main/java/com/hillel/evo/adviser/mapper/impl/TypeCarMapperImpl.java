package com.hillel.evo.adviser.mapper.impl;

import com.hillel.evo.adviser.dto.identification.TypeCarDto;
import com.hillel.evo.adviser.entity.identification.TypeCar;
import com.hillel.evo.adviser.mapper.TypeCarMapper;
import org.springframework.stereotype.Component;

import javax.annotation.processing.Generated;

@Component
public class TypeCarMapperImpl implements TypeCarMapper {

    @Override
    public TypeCarDto toDto(TypeCar entity) {
        if ( entity == null ) {
            return null;
        }

        TypeCarDto typeCarDto = new TypeCarDto();

        typeCarDto.setId( entity.getId() );
        typeCarDto.setName( entity.getName() );

        return typeCarDto;
    }

    @Override
    public TypeCar toEntity(TypeCarDto dto) {
        if ( dto == null ) {
            return null;
        }

        TypeCar typeCar = new TypeCar();

        typeCar.setId( dto.getId() );
        typeCar.setName( dto.getName() );

        return typeCar;
    }
}
