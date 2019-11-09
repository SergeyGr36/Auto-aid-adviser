package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.BusinessApplication;
import com.hillel.evo.adviser.dto.ServiceBusinessDto;
import com.hillel.evo.adviser.entity.ServiceBusiness;
import com.hillel.evo.adviser.exception.DeleteException;
import com.hillel.evo.adviser.mapper.ServiceBusinessMapper;
import com.hillel.evo.adviser.repository.ServiceBusinessRepository;
import com.hillel.evo.adviser.service.impl.ServiceBusinessServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {BusinessApplication.class})
@Sql(value = {"/clean-business.sql", "/clean-user.sql", "/create-user.sql", "/create-business.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ServiceBusinessServiceTest {
    @Autowired
    private ServiceBusinessRepository repo;
    @Autowired
    private ServiceBusinessMapper mapper;
    @Autowired
    private ServiceBusinessServiceImpl service;
    @Test
    public void tryToDeleteThenThrowException() {
        assertThrows(DeleteException.class, () -> service.deleteServiceBusiness(5L));
    }

    @Test
    public void whenGetServiceBusinessByIdThenReturnThisOne() {
        final ServiceBusiness type = repo.findAll().get(0);
        final ServiceBusinessDto dto = service.getServiceBusinessById(type.getId());
        assertEquals(type.getName(), dto.getName());
        assertEquals(type.getId(), dto.getId());
    }

    @Test
    public void whenGetAllByServiceTypeIdThenReturnThisList() {
        List<ServiceBusiness> type;
        List<ServiceBusinessDto> dto = service.getAllByServiceTypeId(1L);
        type = mapper.toEntity(dto);
        for (int i = 0; i < type.size() && i < dto.size(); i++) {
            assertEquals(type.get(i).getName(), dto.get(i).getName());
            assertEquals(type.get(i).getId(), dto.get(i).getId());
        }
    }

    @Test
    public void whenCreateServiceBusinessThenReturn() {
        ServiceBusinessDto dtoSource = new ServiceBusinessDto();
        dtoSource.setName("yyyyy");
        ServiceBusinessDto dtoTarget = service.createServiceBusiness(dtoSource);
        assertEquals(dtoSource.getName(), dtoTarget.getName());
    }

    @Test
    public void whenUpdateServiceBusinessThenReturn() {
        ServiceBusinessDto dtoSource = new ServiceBusinessDto();
        dtoSource.setName("yyyyy");
        ServiceBusinessDto dtoTarget = service.updateServiceBusiness(dtoSource);
        assertEquals(dtoSource.getName(), dtoTarget.getName());
    }
    //todo дописать даний метод
    @Test
    public void tryToDeleteThenReturnNothing() {
        final ServiceBusiness type = repo.findByName("for-delete-test").get();
        final Long id = type.getId();
        assertDoesNotThrow(() -> service.deleteServiceBusiness(id));
        assertTrue(repo.findById(id).isEmpty());
    }
}
