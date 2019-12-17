package com.hillel.evo.adviser.mapper.impl;

import com.hillel.evo.adviser.dto.identification.CarModelDto;
import com.hillel.evo.adviser.entity.identification.CarBrand;
import com.hillel.evo.adviser.entity.identification.CarModel;
import com.hillel.evo.adviser.mapper.CarBrandMapper;
import com.hillel.evo.adviser.mapper.CarModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CarModelMapperImpl implements CarModelMapper {

    @Autowired
    private CarBrandMapper carBrandMapper;

    @Override
    public CarModelDto toDto(CarModel entity) {
        if ( entity == null ) {
            return null;
        }

        CarModelDto carModelDto = new CarModelDto();

        carModelDto.setCarBrand( carBrandMapper.toDto((CarBrand) entity.getParent()) );
        carModelDto.setId( entity.getId() );
        carModelDto.setName( entity.getName() );

        return carModelDto;
    }

    @Override
    public CarModel toEntity(CarModelDto dto) {
        if ( dto == null ) {
            return null;
        }

        CarModel carModel = new CarModel();

        carModel.setParent( carBrandMapper.toEntity( dto.getCarBrand() ) );
        carModel.setId( dto.getId() );
        carModel.setName( dto.getName() );

        return carModel;
    }

    @Override
    public List<CarModelDto> toListDto(List<CarModel> entity) {
        if ( entity == null ) {
            return null;
        }

        List<CarModelDto> list = new ArrayList<CarModelDto>( entity.size() );
        for ( CarModel model : entity ) {
            list.add( toDto( model ) );
        }

        return list;
    }

    @Override
    public List<CarModel> toListEntity(List<CarModelDto> dto) {
        if ( dto == null ) {
            return null;
        }

        List<CarModel> list = new ArrayList<CarModel>( dto.size() );
        for ( CarModelDto model : dto ) {
            list.add( toEntity( model ) );
        }

        return list;
    }
}
