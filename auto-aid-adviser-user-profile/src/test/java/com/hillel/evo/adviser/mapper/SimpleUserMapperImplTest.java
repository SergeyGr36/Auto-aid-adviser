package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.UserProfileStarter;
import com.hillel.evo.adviser.dto.CarBrandDto;
import com.hillel.evo.adviser.dto.SimpleUserDto;
import com.hillel.evo.adviser.entity.CarBrand;
import com.hillel.evo.adviser.entity.SimpleUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(classes = UserProfileStarter.class)
@ExtendWith(SpringExtension.class)
public class SimpleUserMapperImplTest {

    @Autowired
    private SimpleUserMapperImpl mapper;

    @Test
    public void toDtoSetNullThenReturnNull() {
        assertNull(mapper.toDto(null));
    }

    @Test
    public void toDtoThenReturnEntity() {
        SimpleUser entity = getTestEntity();
        SimpleUserDto dto = mapper.toDto(entity);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getFirstName(), dto.getFirstName());
    }

    @Test
    public void toEntitySetNullThenReturnNull() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    public void toEntityThenReturnDto() {
        SimpleUserDto dto = getTestDto();
        SimpleUser entity = mapper.toEntity(dto);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getFirstName(), entity.getFirstName());
    }

    private SimpleUserDto getTestDto() {
        SimpleUserDto dto = new SimpleUserDto(1L, "first", "last", "phone");
        return dto;
    }

    private SimpleUser getTestEntity() {
        SimpleUser entity = new SimpleUser();
        entity.setId(1L);
        entity.setFirstName("fntest");
        entity.setLastName("lntest");
        entity.setPhone("phtest");
        return entity;
    }
}
