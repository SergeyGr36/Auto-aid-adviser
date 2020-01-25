package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.BusinessApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {BusinessApplication.class})
public class BusinessTypeMapperTest {

    @Autowired
    private BusinessTypeMapper businessTypeMapper;

    @Test
    public void whenDtoEqNullReturnNull() {
        Assertions.assertNull(businessTypeMapper.toType(null));
    }

    @Test
    public void whenEntityEqNullReturnNull() {
        Assertions.assertNull(businessTypeMapper.toDto(null));
    }
    @Test
    public void whenListDtoEqNullReturnNull() {
        Assertions.assertNull(businessTypeMapper.toAllType(null));
    }

    @Test
    public void whenListEntityEqNullReturnNull() {
        Assertions.assertNull(businessTypeMapper.toDtoList(null));
    }
}
