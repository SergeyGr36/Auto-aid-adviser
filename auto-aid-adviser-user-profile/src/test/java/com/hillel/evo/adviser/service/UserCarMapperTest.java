package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.UserProfileStarter;
import com.hillel.evo.adviser.dto.MotorTypeDto;
import com.hillel.evo.adviser.dto.UserCarDto;
import com.hillel.evo.adviser.entity.CarBrand;
import com.hillel.evo.adviser.entity.MotorType;
import com.hillel.evo.adviser.entity.UserCar;
import com.hillel.evo.adviser.mapper.UserCarMapper;
import com.hillel.evo.adviser.repository.UserCarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(classes = UserProfileStarter.class)
@ExtendWith(SpringExtension.class)
@Sql(value = "/user-profile.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/alter-user-profile.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserCarMapperTest {


    @Autowired
    UserCarRepository repository;
    @Autowired
    UserCarMapper mapper;


    @Test
    public void whenToDtoSetNullReturnNull() {
        assertNull(mapper.toDto(null));

    }

    @Test
    public void whenCarBrandToDtoSetEntityReturnEntity() {
        UserCar car = new UserCar();
        car.setBrand(new CarBrand(2L, "Audi"));
        UserCarDto dto = mapper.toDto(car);
        assertEquals(dto.getBrandDto().getName(), car.getBrand().getName());
    }

    @Test
    public void whenCarBrandToDtoSetNullReturnNull() {
        UserCar car = new UserCar();
        car.setBrand(new CarBrand());
        UserCarDto dto = mapper.toDto(car);
        assertNull(dto.getBrandDto().getId());
        assertNull(dto.getBrandDto().getName());

    }
}
