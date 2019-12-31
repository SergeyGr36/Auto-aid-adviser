package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.BaseTest;
import com.hillel.evo.adviser.BusinessApplication;
import com.hillel.evo.adviser.dto.ServiceForBusinessDto;
import com.hillel.evo.adviser.entity.ServiceForBusiness;
import com.hillel.evo.adviser.exception.DeleteException;
import com.hillel.evo.adviser.mapper.ServiceForBusinessMapper;
import com.hillel.evo.adviser.repository.ServiceForBusinessRepository;
import com.hillel.evo.adviser.service.impl.ServiceForBusinessServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {BusinessApplication.class})
@Sql(value = {"/clean-all.sql", "/create-user.sql", "/create-business.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ServiceForBusinessServiceTest {
    @Autowired
    private ServiceForBusinessRepository repo;
    @Autowired
    private ServiceForBusinessMapper mapper;
    @Autowired
    private ServiceForBusinessServiceImpl service;
    @Test
    public void tryToDeleteThenThrowException() {
        assertThrows(DeleteException.class, () -> service.deleteServiceBusiness(5L));
    }

    @Test
    public void whenGetServiceBusinessByIdThenReturnThisOne() {
        final ServiceForBusiness type = repo.findAll().get(0);
        final ServiceForBusinessDto dto = service.getServiceBusinessById(type.getId());
        assertEquals(type.getName(), dto.getName());
        assertEquals(type.getId(), dto.getId());
    }

    @Test
    public void whenGetAllByServiceTypeIdThenReturnThisList() {
        List<ServiceForBusiness> type;
        List<ServiceForBusinessDto> dto = service.getAllByServiceTypeId(1L);
        type = mapper.toEntity(dto);
        for (int i = 0; i < type.size() && i < dto.size(); i++) {
            assertEquals(type.get(i).getName(), dto.get(i).getName());
            assertEquals(type.get(i).getId(), dto.get(i).getId());
        }
    }

    @Test
    public void whenFindAllByPages() {
        //given
        final int page = 0;
        final int size = 5;
        //when
        Page<ServiceForBusinessDto> serviceForBusinessDtos = service.byPages(page, size);
        //then
        assertEquals(serviceForBusinessDtos.getContent().size(), size);
        assertEquals(serviceForBusinessDtos.getNumber(), page);
        assertEquals(serviceForBusinessDtos.getSize(), size);
    }

    @Test
    public void whenCreateServiceBusinessThenReturn() {
        ServiceForBusinessDto dtoSource = new ServiceForBusinessDto();
        dtoSource.setName("yyyyy");
        ServiceForBusinessDto dtoTarget = service.createServiceBusiness(dtoSource);
        assertEquals(dtoSource.getName(), dtoTarget.getName());
    }

    @Test
    public void whenUpdateServiceBusinessThenReturn() {
        ServiceForBusinessDto dtoSource = new ServiceForBusinessDto();
        dtoSource.setName("yyyyy");
        ServiceForBusinessDto dtoTarget = service.updateServiceBusiness(dtoSource);
        assertEquals(dtoSource.getName(), dtoTarget.getName());
    }

    @Test
    public void tryToDeleteThenReturnNothing() {
        final ServiceForBusiness type = repo.findByName("for-delete-test").get();
        final Long id = type.getId();
        assertDoesNotThrow(() -> service.deleteServiceBusiness(id));
        assertTrue(repo.findById(id).isEmpty());
    }
}
