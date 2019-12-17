package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.UserProfileStarter;
import com.hillel.evo.adviser.dto.identification.CarBrandDto;
import com.hillel.evo.adviser.dto.identification.CarModelDto;
import com.hillel.evo.adviser.dto.identification.TypeCarDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserProfileStarter.class)
@Sql(value = {"/user-profile.sql", "/create-image.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/clean-user-profile.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CarIdentificationServiceTest {

    @Autowired
    private CarIdentificationService service;

    @Test
    public void findTypeCarByNameThenReturnDto() {
        //given
        String name = "coupe";
        //when
        TypeCarDto dto = service.findTypeCarByName(name);
        //then
        assertNotNull(dto);
        assertEquals(name, dto.getName());
    }

    @Test
    public void findCarBrandByNameThenReturnDto() {
        //given
        String name = "BMW";
        //when
        CarBrandDto dto = service.findCarBrandByName(name);
        //then
        assertNotNull(dto);
        assertEquals(name, dto.getName());
    }

    @Test
    public void findCarModelByNameThenReturnDto() {
        //given
        String name = "M5";
        //when
        CarModelDto dto = service.findCarModelByName(name);
        //then
        assertNotNull(dto);
        assertEquals(name, dto.getName());
    }
}
