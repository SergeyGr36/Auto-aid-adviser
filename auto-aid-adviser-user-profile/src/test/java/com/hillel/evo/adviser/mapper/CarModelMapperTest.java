package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.UserProfileStarter;
import com.hillel.evo.adviser.dto.identification.CarBrandDto;
import com.hillel.evo.adviser.dto.identification.FuelTypeDto;
import com.hillel.evo.adviser.dto.identification.CarModelDto;
import com.hillel.evo.adviser.dto.identification.TypeCarDto;
import com.hillel.evo.adviser.entity.identification.CarBrand;
import com.hillel.evo.adviser.entity.identification.FuelType;
import com.hillel.evo.adviser.entity.identification.CarModel;
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
public class CarModelMapperTest {
    @Autowired
    CarModelMapper carMapper;
//    to dto
    @Test
    public void whenCarBrandNullReturnNull(){
        assertNull(carMapper.toDto((CarModel) null));
    }

/*
    @Test
    public void whenSetTypeCarThenReturnDto(){
        TypeCar typeCar = new TypeCar(1L, "coupe");
        TypeCarDto dto = carMapper.toDto(typeCar);
        assertEquals(typeCar.getName(), dto.getName());
    }
*/
    @Test
    public void whenSetCarModelThenReturnDto() {
        CarModel carModel = getCarModel();
        CarModelDto dto = carMapper.toDto(carModel);
        assertEquals(carModel.getName(), dto.getName());
    }

//    to entity
/*
    @Test
    public void whenTypeCarDtoNullReturnNull(){
        assertNull(carMapper.toEntity((TypeCarDto) null));
    }
*/
    @Test
    public void whenCarModelDtoNullReturnNull(){
        assertNull(carMapper.toEntity((CarModelDto) null));
    }

@Test
public void whenSetCarModelDtoThenReturnEntity(){
    CarModelDto dto = getCarModelDto();
    CarModel carModel = carMapper.toEntity(dto);
    assertEquals(dto.getName(), carModel.getName());
}
/*
    @Test
    public void whenSetCarBrandDtoThenReturnEntity(){
        TypeCarDto typeCarDto = new TypeCarDto(1L, "coupe");
        CarBrandDto dto = new CarBrandDto(1L, "audi", typeCarDto);
        CarBrand brand = carMapper.toEntity(dto);
        assertEquals(brand.getName(), dto.getName());
    }
*/

    private CarModelDto getCarModelDto() {
        TypeCarDto typeCar = new TypeCarDto(1L, "Легковой");
        CarBrandDto carBrand = new CarBrandDto(2L, "Mersedes", typeCar);
        CarModelDto carModel = new CarModelDto(3L, "C-class", carBrand);

        return carModel;
    }

    private CarModel getCarModel() {
        TypeCar typeCar = new TypeCar(1L, "TestType");
        CarBrand carBrand = new CarBrand(2L, "TestBrand", typeCar);
        CarModel carModel = new CarModel(3L, "TestModel", carBrand);

        return carModel;
    }
}
