package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.BusinessApplication;
import com.hillel.evo.adviser.configuration.HibernateSearchConfig;
import com.hillel.evo.adviser.dto.BusinessTypeDto;
import com.hillel.evo.adviser.entity.BusinessType;
import com.hillel.evo.adviser.exception.DeleteException;
import com.hillel.evo.adviser.mapper.BusinessTypeMapper;
import com.hillel.evo.adviser.repository.BusinessTypeRepository;
import com.hillel.evo.adviser.service.impl.BusinessTypeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {BusinessApplication.class})
@Sql(value = {"/clean-business.sql", "/clean-user.sql", "/create-user.sql", "/create-business.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/clean-business.sql", "/clean-user.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class BusinessTypeServiceImplTest {
    @Autowired
    private BusinessTypeServiceImpl businessTypeService;
    @Autowired
    private BusinessTypeMapper mapper;
    @Autowired
    private BusinessTypeRepository businessTypeRepository;
    @Autowired
    private HibernateSearchConfig hibernateSearchConfig;

    @Test
    public void tryToDeleteThenThrowException() {
        assertThrows(DeleteException.class, () -> businessTypeService.deleteBusinessType(5L));
    }

    @Test
    public void whenDeleteThenNotException() {
        //given
        BusinessType type = businessTypeRepository.findByName("test").get();
        //then
        assertDoesNotThrow(() -> businessTypeService.deleteBusinessType(type.getId()));
    }

    @Test
    public void whenGetBusinessTypeByIdThenReturnThisOne() {
        BusinessType type = businessTypeRepository.findAll().get(0);
        final BusinessTypeDto dto = businessTypeService.findBusinessTypeById(type.getId());
        assertEquals(type.getName(), dto.getName());
        assertEquals(type.getId(), dto.getId());
    }

    @Test
    public void whenFindAllThenReturnThisList() {
        List<BusinessType> type;
        List<BusinessTypeDto> dto = businessTypeService.findAll();
        type = mapper.toAllType(dto);
        for (int i = 0; i < type.size() && i < dto.size(); i++) {
            assertEquals(type.get(i).getName(), dto.get(i).getName());
            assertEquals(type.get(i).getId(), dto.get(i).getId());
        }
    }

    @Test
    public void whenFindAllByNameThenReturnThisList() {

        hibernateSearchConfig.reindex(BusinessType.class);
        var result = businessTypeService.findAllByName("shop");
        assertEquals(1, result.size());
    }

    @Test
    public void whenFindAllByNameContainsThenReturnThisList() {

        hibernateSearchConfig.reindex(BusinessType.class);
        var result = businessTypeService.findAllByNameContains("sh*");
        assertEquals(2, result.size());
    }

    @Test
    public void whenCreateBusinessTypeThenReturn() {
        BusinessTypeDto dtoSource = new BusinessTypeDto();
        dtoSource.setName("yyyyy");
        BusinessTypeDto dtoTarget = businessTypeService.createBusinessType(dtoSource);
        assertEquals(dtoSource.getName(), dtoTarget.getName());
    }

    @Test
    public void whenUpdateBusinessTypeThenReturn() {
        BusinessTypeDto dtoSource = mapper.toDto(businessTypeRepository.findByName("test").get());
        dtoSource.setName("yyyyy");
        BusinessTypeDto dtoTarget = businessTypeService.updateBusinessType(dtoSource);
        assertEquals(dtoSource.getName(), dtoTarget.getName());
    }

/*
    @Test
    public void tryToDeleteThenReturnNothing() {
        businessTypeRepository.deleteAllInBatch();
        assertNull(businessTypeRepository.findAll());
    }
*/

}
