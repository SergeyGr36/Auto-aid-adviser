package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.UserProfileStarter;
import com.hillel.evo.adviser.dto.identification.CarIdentificationDto;
import com.hillel.evo.adviser.entity.identification.CarBrand;
import com.hillel.evo.adviser.entity.identification.CarIdentification;
import com.hillel.evo.adviser.entity.identification.MotorType;
import com.hillel.evo.adviser.entity.identification.TypeCar;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(classes = UserProfileStarter.class)
@ExtendWith(SpringExtension.class)
public class AbstractCarIdentificationMapperImplTest {
    @Autowired
    AbstractCarIdentificationMapperImpl mapper;
    @Test
    public void whenGetCarBrandInstanceThenWorksWithIt(){
        CarIdentificationDto dto;
        CarIdentification carIdentification = new CarBrand(1L, "Audi");
       dto = mapper.toDto(carIdentification);
        assertEquals(dto.getId(), carIdentification.getId());
        assertEquals(dto.getName(), carIdentification.getName());
    }
    @Test
    public void whenGetMotorTypeInstanceThenWorksWithIt(){
        CarIdentificationDto dto;
        CarIdentification carIdentification = new MotorType(1L, "mechanic");
        dto = mapper.toDto(carIdentification);
        assertEquals(dto.getId(), carIdentification.getId());
        assertEquals(dto.getName(), carIdentification.getName());
    }
    @Test
    public void whenGetTypeCarInstanceThenWorksWithIt(){
        CarIdentificationDto dto;
        CarIdentification carIdentification = new TypeCar(1L, "coupe");
        dto = mapper.toDto(carIdentification);
        assertEquals(dto.getId(), carIdentification.getId());
        assertEquals(dto.getName(), carIdentification.getName());
    }
    @Test
    public void whenSetNullThenReturnNull(){
        CarIdentificationDto dto = mapper.toDto(null);
        assertNull(dto);
    }
}
