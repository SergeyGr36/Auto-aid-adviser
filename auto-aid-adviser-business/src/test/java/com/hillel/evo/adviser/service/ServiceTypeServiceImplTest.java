package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.BusinessApplication;
import com.hillel.evo.adviser.configuration.HibernateSearchConfig;
import com.hillel.evo.adviser.dto.ServiceTypeDto;
import com.hillel.evo.adviser.entity.ServiceType;
import com.hillel.evo.adviser.exception.DeleteException;
import com.hillel.evo.adviser.mapper.ServiceTypeMapper;
import com.hillel.evo.adviser.repository.ServiceTypeRepository;
import com.hillel.evo.adviser.service.impl.ServiceTypeServiceImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = {BusinessApplication.class})
@Sql(value = {"/clean-business.sql", "/clean-user.sql", "/create-user.sql", "/create-business.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/clean-business.sql", "/clean-user.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ServiceTypeServiceImplTest {
    @Autowired
    private ServiceTypeRepository repo;
    @Autowired
    private ServiceTypeMapper mapper;
    @Autowired
    private ServiceTypeServiceImpl service;
    @Autowired
    private HibernateSearchConfig hibernateSearchConfig;

    @Test
    public void tryToDeleteThenThrowException() {
        assertThrows(DeleteException.class, () -> service.deleteServiceType(5L));
    }

    @Test
    public void tryToDeleteThenNotThrowException() {
        ServiceType type = repo.findByName("test").get();
        assertDoesNotThrow(() -> service.deleteServiceType(type.getId()));
    }

    @Test
    public void whenGetServiceTypeByIdThenReturnThisOne() {
        final ServiceType type = repo.findAll().get(0);
        final ServiceTypeDto dto = service.getServiceTypeById(type.getId());
        assertEquals(type.getName(), dto.getName());
        assertEquals(type.getId(), dto.getId());
    }

/*
    @Test
    public void whenFindAllByNameThenReturnThisList() {
        hibernateSearchConfig.reindex(ServiceType.class);
        var result = service.findAllByName("body");
        assertEquals(1, result.size());
    }

    @Test
    public void whenFindAllByNameContainsThenReturnThisList() {
        hibernateSearchConfig.reindex(ServiceType.class);
        var result = service.findAllByNameContains("ru*", "shinomantazh");
        assertEquals(1, result.size());
    }
*/

    @Test
    public void whenFindAllByServiceTypeIdThenReturnThisList() {
        List<ServiceTypeDto> dto = service.findAllByBusinessTypeId(1L);
        List<ServiceType> type = mapper.toEntity(dto);
        for (int i = 0; i < type.size() && i < dto.size(); i++) {
            assertEquals(type.get(i).getName(), dto.get(i).getName());
            assertEquals(type.get(i).getId(), dto.get(i).getId());
        }
    }

    @Test
    public void whenFindAllByPages() {
        //geven
        final int page = 0;
        final int size = 5;
        //when
        Page<ServiceTypeDto> allByPages = service.findAllByPages(page, size);
        //then
        assertEquals(page, allByPages.getNumber());
        assertEquals(size, allByPages.getSize());
        assertEquals(size, allByPages.getContent().size());
    }

    @Test
    public void whenCreateServiceTypeThenReturn() {
        ServiceTypeDto dtoSource = new ServiceTypeDto();
        dtoSource.setName("yyyyy");
        ServiceTypeDto dtoTarget = service.createServiceType(dtoSource);
        assertEquals(dtoSource.getName(), dtoTarget.getName());
    }

    @Test
    public void whenUpdateServiceTypeThenReturn() {
        //given
        ServiceTypeDto dtoSource = mapper.toDto(repo.findByName("body").get());
        //when
        dtoSource.setName("yyyyy");
        ServiceTypeDto dtoTarget = service.updateServiceType(dtoSource);
        //then
        assertEquals(dtoSource.getName(), dtoTarget.getName());
    }
}
