package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.CarIdentificationDto;
import com.hillel.evo.adviser.entity.CarBrand;
import com.hillel.evo.adviser.entity.CarIdentification;
import com.hillel.evo.adviser.entity.MotorType;
import com.hillel.evo.adviser.entity.TypeCar;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = UserCarMapper.class)
public abstract class CarIdentificationMapper {
//    @Mapping(target = "id", source = "dto.id")
//    @Mapping(target = "name", source = "dto.name")
//    CarIdentification toEntity(CarIdentificationDto dto);
//    @Mapping(target = "id", source = "identification.id")
//    @Mapping(target = "name", source = "identification.name")
//    CarIdentificationDto toDto(CarIdentification identification);

    private CarBrand brand;
    private MotorType motorType;
    private TypeCar typeCar;

//    public CarIdentificationMapper(CarBrand brand, MotorType motorType, TypeCar typeCar) {
//        this.brand = brand;
//        this.motorType = motorType;
//        this.typeCar = typeCar;
//    }

    @BeforeMapping
    public void getActualChildClass(CarIdentification identification, @MappingTarget CarIdentificationDto dto){
        if(identification instanceof CarBrand){
            dto.setId(brand.getId());
            dto.setName(brand.getName());
        } else if(identification instanceof MotorType){
            dto.setId(motorType.getId());
            dto.setName(motorType.getName());
        } else if(identification instanceof TypeCar){
            dto.setId(typeCar.getId());
            dto.setName(typeCar.getName());
        }
    }
    public abstract CarIdentificationDto toDto(CarIdentification carIdentification);
}
