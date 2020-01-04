package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.UserProfileStarter;
import com.hillel.evo.adviser.dto.CarModelDto;
import com.hillel.evo.adviser.entity.CarBrand;
import com.hillel.evo.adviser.entity.CarModel;
import com.hillel.evo.adviser.entity.TypeCar;
import com.hillel.evo.adviser.repository.CarBrandRepository;
import com.hillel.evo.adviser.repository.CarModelRepository;
import com.hillel.evo.adviser.repository.TypeCarRepository;
import com.hillel.evo.adviser.service.impl.CarModelServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserProfileStarter.class)
@Sql(value = {"/user-profile.sql", "/create-image.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/clean-user-profile.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CarModelServiceImplTest {

    @Autowired
    private CarModelServiceImpl carModelService;

    @Autowired
    private TypeCarRepository typeCarRepository;

    @Autowired
    private CarBrandRepository carBrandRepository;

    @Autowired
    private CarModelRepository carModelRepository;

    @Test
    public void findModelByIdReturnDto() {
        //given
        CarModel carModel = carModelRepository.findAll().get(0);
        //when
        CarModelDto dto = carModelService.findById(carModel.getId());
        //then
        assertEquals(carModel.getId(), dto.getId());
    }

    @Test
    public void findModelByIdReturnException() {
        assertThrows(RuntimeException.class, () -> carModelService.findById(99L));
    }

    @Test
    public void findModelByNameReturnDto() {
        //given
        String name = "X5";
        //when
        CarModelDto dto = carModelService.findByName(name);
        //then
        assertEquals(name, dto.getName());
    }

    @Test
    public void findModelByNameReturnException() {
        assertThrows(RuntimeException.class, () -> carModelService.findByName("someName"));
    }

    @Test
    public void findModelByTypeIdAndBrandIdReturnListDto() {
        //given
        TypeCar crossover = typeCarRepository.findByName("crossover").get();
        CarBrand bmw = carBrandRepository.findByName("BMW").get();
        //when
        List<CarModelDto> list = carModelService.findByTypeAndBrand(crossover.getId(), bmw.getId());
        //then
        assertEquals(2, list.size());
    }

    @Test
    public void findModelByTypeNameAndBrandNameReturnListDto() {
        //given
        TypeCar crossover = typeCarRepository.findByName("crossover").get();
        CarBrand bmw = carBrandRepository.findByName("BMW").get();
        //when
        List<CarModelDto> list = carModelService.findByTypeAndBrand(crossover.getName(), bmw.getName());
        //then
        assertEquals(2, list.size());
    }

}
