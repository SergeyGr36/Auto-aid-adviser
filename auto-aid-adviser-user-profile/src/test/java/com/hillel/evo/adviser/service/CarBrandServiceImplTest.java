package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.UserProfileStarter;
import com.hillel.evo.adviser.dto.CarBrandDto;
import com.hillel.evo.adviser.entity.CarBrand;
import com.hillel.evo.adviser.repository.CarBrandRepository;
import com.hillel.evo.adviser.service.impl.CarBrandServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserProfileStarter.class)
@Sql(value = {"/user-profile.sql", "/create-image.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/clean-user-profile.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CarBrandServiceImplTest {

    @Autowired
    private CarBrandServiceImpl carBrandService;

    @Autowired
    private CarBrandRepository carBrandRepository;


    @Test
    public void findBrandByIdReturnDto() {
        //given
        CarBrand carBrand = carBrandRepository.findAll().get(0);
        //when
        CarBrandDto dto = carBrandService.findById(carBrand.getId());
        //then
        assertNotNull(dto);
        assertEquals(carBrand.getId(), dto.getId());
    }

    @Test
    public void findBrandByNameReturnDto() {
        //given
        String name = "BMW";
        //when
        CarBrandDto dto = carBrandService.findByName(name);
        //then
        assertNotNull(dto);
        assertEquals(name, dto.getName());
    }

    @Test
    public void findAllBrandReturnList() {
        List<CarBrandDto> list = carBrandService.findAll();
        //then
        assertEquals(2, list.size());
    }

    @Test
    public void findBrandByIdReturnException() {
        assertThrows(RuntimeException.class, () -> carBrandService.findById(99L));
    }

    @Test
    public void findBrandByNameReturnException() {
        assertThrows(RuntimeException.class, () -> carBrandService.findByName("someName"));
    }

}
