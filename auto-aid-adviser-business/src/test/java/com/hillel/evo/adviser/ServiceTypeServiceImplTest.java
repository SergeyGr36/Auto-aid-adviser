package com.hillel.evo.adviser;

import com.hillel.evo.adviser.dto.BusinessTypeDto;
import com.hillel.evo.adviser.dto.ServiceTypeDto;
import com.hillel.evo.adviser.entity.BusinessType;
import com.hillel.evo.adviser.entity.ServiceType;
import com.hillel.evo.adviser.exception.DeleteException;
import com.hillel.evo.adviser.mapper.ServiceTypeMapper;
import com.hillel.evo.adviser.repository.ServiceTypeRepository;
import com.hillel.evo.adviser.service.impl.ServiceTypeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {BusinessApplication.class})
@Sql(value = {"/clean-business.sql", "/clean-user.sql", "/create-user.sql", "/create-business.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ServiceTypeServiceImplTest {
    @Autowired
    private ServiceTypeRepository repo;
    @Autowired
    private ServiceTypeMapper mapper;
    @Autowired
    private ServiceTypeServiceImpl service;

    @Test
    public void tryToDeleteThenThrowException() {
        assertThrows(DeleteException.class, () -> service.deleteServiceType(5L));
    }

    @Test
    public void whenGetServiceTypeByIdThenReturnThisOne() {
        final ServiceType type = repo.findAll().get(0);
        final ServiceTypeDto dto = service.getServiceTypeById(type.getId());
        assertEquals(type.getType(), dto.getType());
        assertEquals(type.getId(), dto.getId());
    }

    @Test
    public void whenFindAllByServiceTypeIdThenReturnThisList() {
        List<ServiceType> type;
        List<ServiceTypeDto> dto = service.findAllByServiceTypeId(1L);
        type = mapper.toEntity(dto);
        for (int i = 0; i < type.size() && i < dto.size(); i++) {
            assertEquals(type.get(i).getType(), dto.get(i).getType());
            assertEquals(type.get(i).getId(), dto.get(i).getId());
        }
    }

    @Test
    public void whenCreateServiceTypeThenReturn() {
        ServiceTypeDto dtoSource = new ServiceTypeDto();
        dtoSource.setType("yyyyy");
        ServiceTypeDto dtoTarget = service.createServiceType(dtoSource);
        assertEquals(dtoSource.getType(), dtoTarget.getType());
    }

    @Test
    public void whenUpdateServiceTypeThenReturn() {
        ServiceTypeDto dtoSource = new ServiceTypeDto();
        dtoSource.setType("yyyyy");
        ServiceTypeDto dtoTarget = service.updateServiceType(dtoSource);
        assertEquals(dtoSource.getType(), dtoTarget.getType());
    }
//  //todo дописать даний метод
//    @Test
//    public void tryToDeleteThenReturnNothing() {
//        businessTypeRepository.deleteAllInBatch();
//        assertNull(businessTypeRepository.findAll());
//    }
}
