package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.identification.CarIdentificationDto;
import com.hillel.evo.adviser.entity.identification.CarBrand;
import com.hillel.evo.adviser.entity.identification.CarIdentification;
import com.hillel.evo.adviser.entity.identification.MotorType;
import com.hillel.evo.adviser.entity.identification.TypeCar;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = UserCarMapper.class)
public abstract class AbstractCarIdentificationMapper {

    private transient CarBrand brand;
    private transient MotorType motorType;
    private transient TypeCar typeCar;


    @BeforeMapping
    public void getActualChildClass(CarIdentification identification, @MappingTarget CarIdentificationDto dto){
        if(identification instanceof CarBrand){
            brand = (CarBrand) identification;
            dto.setId(brand.getId());
            dto.setName(brand.getName());
        } else if(identification instanceof MotorType){
            motorType = (MotorType) identification;
            dto.setId(motorType.getId());
            dto.setName(motorType.getName());
        } else{
            typeCar = (TypeCar)identification;
            dto.setId(typeCar.getId());
            dto.setName(typeCar.getName());
        }
    }
    public abstract CarIdentificationDto toDto(CarIdentification carIdentification);
}
