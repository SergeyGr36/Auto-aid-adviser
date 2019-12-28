package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.UserProfileStarter;
import com.hillel.evo.adviser.dto.CarBrandDto;
import com.hillel.evo.adviser.dto.CarModelDto;
import com.hillel.evo.adviser.dto.TypeCarDto;
import com.hillel.evo.adviser.entity.CarBrand;
import com.hillel.evo.adviser.entity.CarModel;
import com.hillel.evo.adviser.entity.TypeCar;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(classes = UserProfileStarter.class)
@ExtendWith(SpringExtension.class)
public class CarModelMapperImplTest {

    @Autowired
    CarModelMapperImpl carMapper;

    //    to dto
    @Test
    public void whenCarModelNullReturnNull(){
        assertNull(carMapper.toDto( null));
    }

    @Test
    public void whenListDtoNullReturnNull(){
        assertNull(carMapper.toListDto( null));
    }

    @Test
    public void whenSetCarModelThenReturnDto() {
        CarModel carModel = getCarModel();
        CarModelDto dto = carMapper.toDto(carModel);
        assertEquals(carModel.getName(), dto.getName());
    }

    @Test
    public void whenListCarModelThenReturnDto() {
        CarModel carModel = getCarModel();
        List<CarModel> list = Arrays.asList(carModel);
        List<CarModelDto> result = carMapper.toListDto(list);
        assertEquals(list.size(), result.size());
        assertEquals(list.get(0).getName(), result.get(0).getName());
    }

    //    to entity
    @Test
    public void whenCarModelDtoNullReturnNull(){
        assertNull(carMapper.toEntity( null));
    }

    @Test
    public void whenListEntityNullReturnNull(){
        assertNull(carMapper.toListEntity( null));
    }

    @Test
    public void whenSetCarModelDtoThenReturnEntity(){
        CarModelDto dto = getCarModelDto();
        CarModel carModel = carMapper.toEntity(dto);
        assertEquals(dto.getName(), carModel.getName());
    }

    @Test
    public void whenListCarModelDtoThenReturnListEntity(){
        CarModelDto carModelDto = getCarModelDto();
        List<CarModelDto> list = Arrays.asList(carModelDto);
        List<CarModel> result = carMapper.toListEntity(list);
        assertEquals(list.size(), result.size());
        assertEquals(list.get(0).getName(), result.get(0).getName());
    }

    private CarModelDto getCarModelDto() {
        TypeCarDto typeCar = new TypeCarDto(1L, "Легковой");
        CarBrandDto carBrand = new CarBrandDto(1L, "Mersedes");
        CarModelDto carModel = new CarModelDto(1L, "C-class", carBrand, typeCar);

        return carModel;
    }

    private CarModel getCarModel() {
        TypeCar typeCar = new TypeCar(1L, "TestType");
        CarBrand carBrand = new CarBrand(1L, "TestBrand");
        CarModel carModel = new CarModel(1L, "TestModel", carBrand, typeCar);

        return carModel;
    }
}
