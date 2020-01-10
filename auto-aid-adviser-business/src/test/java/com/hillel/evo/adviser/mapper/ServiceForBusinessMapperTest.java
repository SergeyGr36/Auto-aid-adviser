package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.BusinessApplication;
import com.hillel.evo.adviser.dto.ServiceForBusinessDto;
import com.hillel.evo.adviser.dto.ServiceForBusinessShortDto;
import com.hillel.evo.adviser.entity.ServiceForBusiness;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {BusinessApplication.class})
@Sql(value = {"/clean-all.sql", "/create-user.sql", "/create-business.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ServiceForBusinessMapperTest {

    @Autowired
    ServiceForBusinessMapper mapper;

    @Test
    public void whenToDto_SetNull_ReturnNull() {
        Assertions.assertNull(mapper.toDto((ServiceForBusiness) null));
    }

    @Test
    public void whenToDto_SetNullList_ReturnEmptyList() {
        Assertions.assertNull(mapper.toDtoList((List<ServiceForBusiness>) null));
    }

    @Test
    public void whenToDto_NullSet_ReturnEmptySet() {
        Assertions.assertNull(mapper.toDto((Set<ServiceForBusiness>) null));
    }

    @Test
    public void whenToDto_SetEmptyList_ReturnEmptyList() {
        Assertions.assertTrue(mapper.toDtoList(new ArrayList<>()).isEmpty());
    }

    @Test
    public void whenToDto_EmptySet_ReturnEmptySet() {
        Assertions.assertTrue(mapper.toDto(new HashSet<>()).isEmpty());
    }

    @Test
    public void whenToDto_SetList_ReturnListDto() {
        List<ServiceForBusiness> list = Arrays.asList(new ServiceForBusiness());
        Assertions.assertEquals(mapper.toDtoList(list).size(), list.size());
    }

    @Test
    public void whenToDto_ReturnSetDto() {
        Set<ServiceForBusiness> set = new HashSet<>(Arrays.asList(new ServiceForBusiness()));
        Assertions.assertEquals(mapper.toDto(set).size(), set.size());
    }

    @Test
    public void whenToEntity_SetNull_ReturnNull() {
        Assertions.assertNull(mapper.toEntity((ServiceForBusinessDto) null));
    }

    @Test
    public void whenToEntity_SetNullList_ReturnEmptyList() {
        Assertions.assertNull(mapper.toEntity((List<ServiceForBusinessDto>) null));
    }

    @Test
    public void whenToEntity_SetNullSet_ReturnEmptySet() {
        Assertions.assertNull(mapper.toEntity((Set<ServiceForBusinessDto>) null));
    }

    @Test
    public void whenToEntity_SetEmptyList_ReturnEmptyList() {
        List<ServiceForBusinessDto> list = new ArrayList<>();
        Assertions.assertTrue(mapper.toEntity(list).isEmpty());
    }

    @Test
    public void whenToEntity_EmptySet_ReturnEmptySet() {
        Set<ServiceForBusinessDto> list = new HashSet<>();
        Assertions.assertTrue(mapper.toEntity(list).isEmpty());
    }

    @Test
    public void whenToEntity_ReturnListEntity() {
        List<ServiceForBusinessDto> list = Arrays.asList(new ServiceForBusinessDto());
        Assertions.assertEquals(mapper.toEntity(list).size(), list.size());
    }

    @Test
    public void whenToEntity_ReturnSetEntity() {
        Set<ServiceForBusinessDto> list = new HashSet<>(Arrays.asList(new ServiceForBusinessDto()));
        Assertions.assertEquals(mapper.toEntity(list).size(), list.size());
    }

    @Test
    public void whenToEntity_ReturnNull() {
        Assertions.assertNull(mapper.toEntity((ServiceForBusinessShortDto) null));
    }
}
