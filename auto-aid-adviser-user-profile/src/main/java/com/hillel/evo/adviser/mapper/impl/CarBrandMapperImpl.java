package com.hillel.evo.adviser.mapper.impl;

import com.hillel.evo.adviser.dto.identification.CarBrandDto;
import com.hillel.evo.adviser.entity.identification.CarBrand;
import com.hillel.evo.adviser.entity.identification.TypeCar;
import com.hillel.evo.adviser.mapper.CarBrandMapper;
import com.hillel.evo.adviser.mapper.TypeCarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CarBrandMapperImpl implements CarBrandMapper {

    @Autowired
    private TypeCarMapper typeCarMapper;

    @Override
    public CarBrandDto toDto(CarBrand entity) {
        if ( entity == null ) {
            return null;
        }

        CarBrandDto carBrandDto = new CarBrandDto();

        carBrandDto.setTypeCar( typeCarMapper.toDto((TypeCar) entity.getParent()) );
        carBrandDto.setId( entity.getId() );
        carBrandDto.setName( entity.getName() );

        return carBrandDto;
    }

    @Override
    public CarBrand toEntity(CarBrandDto dto) {
        if ( dto == null ) {
            return null;
        }

        CarBrand carBrand = new CarBrand();

        carBrand.setParent( typeCarMapper.toEntity( dto.getTypeCar() ) );
        carBrand.setId( dto.getId() );
        carBrand.setName( dto.getName() );

        return carBrand;
    }

    @Override
    public List<CarBrand> toListEntity(List<CarBrandDto> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<CarBrand> list = new ArrayList<CarBrand>( dtos.size() );
        for ( CarBrandDto dto : dtos ) {
            list.add( toEntity( dto ) );
        }

        return list;
    }

    @Override
    public List<CarBrandDto> toListDto(List<CarBrand> brands) {
        if ( brands == null ) {
            return null;
        }

        List<CarBrandDto> list = new ArrayList<CarBrandDto>( brands.size() );
        for ( CarBrand brand : brands ) {
            list.add( toDto( brand ) );
        }

        return list;
    }

}
