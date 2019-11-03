package com.hillel.evo.advisor.business;

import com.hillel.evo.adviser.business.dto.BusinessDTO;
import com.hillel.evo.adviser.business.entity.*;
import com.hillel.evo.adviser.business.services.BusinessCrudService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BusinessCrudService.class)
@Sql(value = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class BusinessCrudServiceTest {
    private BusinessCrudService businessCrudService;
    @Test
    public void whenCreateBusinessThenReturnThisOne(){
//        final BusinessDTO dto = new BusinessDTO("У Васи", new Location(1000, 1000, "Kiev"),
//                new Contact("0989889669"), "Monday", "10-20", new TypeBusiness("STO"),
//                new ArrayList<ServiceBusiness>());
//        businessCrudService.createBusiness(dto);
//        assertEquals(dto, businessCrudService.createBusiness(dto));
//        final Business test = new Business();
    }
}
