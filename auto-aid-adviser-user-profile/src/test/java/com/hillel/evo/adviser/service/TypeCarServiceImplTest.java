package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.UserProfileStarter;
import com.hillel.evo.adviser.dto.CarBrandDto;
import com.hillel.evo.adviser.dto.CarModelDto;
import com.hillel.evo.adviser.dto.TypeCarDto;
import com.hillel.evo.adviser.entity.TypeCar;
import com.hillel.evo.adviser.repository.TypeCarRepository;
import com.hillel.evo.adviser.service.impl.TypeCarServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserProfileStarter.class)
@Sql(value = {"/user-profile.sql", "/create-image.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/clean-user-profile.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class TypeCarServiceImplTest {

    @Autowired
    private TypeCarServiceImpl service;
    @Autowired
    private TypeCarRepository typeCarRepository;

    @Test
    public void findTypeCarByNameThenReturnDto() {
        //given
        String name = "coupe";
        //when
        TypeCarDto dto = service.findByName(name);
        //then
        assertNotNull(dto);
        assertEquals(name, dto.getName());
    }

    @Test
    public void findTypeCarByIdThenReturnException() {
        assertThrows(RuntimeException.class, () -> service.findById(99L));
    }

    @Test
    public void findTypeCarByNameThenReturnException() {
        assertThrows(RuntimeException.class, () -> service.findByName("Some"));
    }

    @Test
    public void findTypeCarByIdThenReturnDto() {
        //given
        TypeCar typeCar = typeCarRepository.findAll().get(0);
        //when
        TypeCarDto dto = service.findById(typeCar.getId());
        //then
        assertNotNull(dto);
        assertEquals(typeCar.getId(), dto.getId());
    }

    @Test
    public void findAllTypeCarByIdThenReturnListDto() {
        //when
        List<TypeCarDto> list = service.findAll();
        //then
        assertEquals(2, list.size());
    }

}
