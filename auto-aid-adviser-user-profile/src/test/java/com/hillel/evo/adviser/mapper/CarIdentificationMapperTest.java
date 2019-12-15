package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.UserProfileStarter;
import com.hillel.evo.adviser.dto.identification.CarBrandDto;
import com.hillel.evo.adviser.dto.identification.FuelTypeDto;
import com.hillel.evo.adviser.dto.identification.MotorTypeDto;
import com.hillel.evo.adviser.dto.identification.TypeCarDto;
import com.hillel.evo.adviser.entity.identification.CarBrand;
import com.hillel.evo.adviser.entity.identification.FuelType;
import com.hillel.evo.adviser.entity.identification.MotorType;
import com.hillel.evo.adviser.entity.identification.TypeCar;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(classes = UserProfileStarter.class)
@ExtendWith(SpringExtension.class)
public class CarIdentificationMapperTest {
    @Autowired
    CarIdentificationMapperImpl carMapper;
//    to dto
    @Test
    public void whenCarBrandNullReturnNull(){
        assertNull(carMapper.toDto((CarBrand) null));
    }
    @Test
    public void whenTypeCarNullReturnNull(){
        assertNull(carMapper.toDto((TypeCar) null));
    }
    @Test
    public void whenMotorTypeNullReturnNull(){
        assertNull(carMapper.toDto((MotorType) null));
    }
    @Test
    public void whenFuelTypeNullReturnNull(){
        assertNull(carMapper.toDto((FuelType) null));
    }
    @Test
    public void whenSetMotorTypeThenReturnDto(){
        MotorType motorType = new MotorType(1L, "mechanic");
        MotorTypeDto dto = carMapper.toDto(motorType);
        assertEquals(motorType.getName(), dto.getName());
    }
    @Test
    public void whenSetTypeCarThenReturnDto(){
        TypeCar typeCar = new TypeCar(1L, "coupe");
        TypeCarDto dto = carMapper.toDto(typeCar);
        assertEquals(typeCar.getName(), dto.getName());
    }
    @Test
    public void whenSetCarBrandThenReturnDto() {
        CarBrand car = new CarBrand(2L, "Audi");
        CarBrandDto dto = carMapper.toDto(car);
        assertEquals(dto.getName(), car.getName());
    }
    @Test
    public void whenSetFuelTypeThenReturnDto() {
        FuelType fuelType = new FuelType(2L, "gas");
        FuelTypeDto dto = carMapper.toDto(fuelType);
        assertEquals(dto.getName(), fuelType.getName());
    }
//    to entity
@Test
public void whenMotorTypeDtoNullReturnNull(){
    assertNull(carMapper.toEntity((MotorTypeDto) null));
}
    @Test
    public void whenTypeCarDtoNullReturnNull(){
        assertNull(carMapper.toEntity((TypeCarDto) null));
    }
    @Test
    public void whenCarBrandDtoNullReturnNull(){
        assertNull(carMapper.toEntity((CarBrandDto) null));
    }
    @Test
    public void whenFuelTypeDtoNullReturnNull(){
        assertNull(carMapper.toEntity((FuelTypeDto) null));
    }
@Test
public void whenSetTypeCarDtoThenReturnEntity(){
    TypeCarDto dto = new TypeCarDto(1L, "coupe");
    TypeCar typeCar = carMapper.toEntity(dto);
    assertEquals(typeCar.getName(), dto.getName());
}
    @Test
    public void whenSetCarBrandDtoThenReturnEntity(){
        CarBrandDto dto = new CarBrandDto(1L, "audi");
        CarBrand brand = carMapper.toEntity(dto);
        assertEquals(brand.getName(), dto.getName());
    }
    @Test
    public void whenSetMotorTypeDtoThenReturnEntity(){
        MotorTypeDto dto = new MotorTypeDto(1L, "mechanic");
        MotorType motorType = carMapper.toEntity(dto);
        assertEquals(motorType.getName(), dto.getName());
    }
    @Test
    public void whenSetFuelTypeDtoThenReturnEntity(){
        FuelTypeDto dto = new FuelTypeDto(1L, "gas");
        FuelType fuelType = carMapper.toEntity(dto);
        assertEquals(fuelType.getName(), dto.getName());
    }
}
