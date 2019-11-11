package com.hillel.evo.adviser.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillel.evo.adviser.AdviserStarter;
import com.hillel.evo.adviser.dto.BusinessTypeDto;
import com.hillel.evo.adviser.dto.ServiceBusinessDto;
import com.hillel.evo.adviser.dto.ServiceTypeDto;
import com.hillel.evo.adviser.entity.ServiceBusiness;
import com.hillel.evo.adviser.repository.BusinessTypeRepository;
import com.hillel.evo.adviser.service.BusinessTypeService;
import com.hillel.evo.adviser.service.ServiceBusinessService;
import com.hillel.evo.adviser.service.ServiceTypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AdviserStarter.class)
@AutoConfigureMockMvc
@Sql(value = {"/clean-business.sql", "/clean-user.sql", "/create-user2.sql", "/create-business.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/clean-business.sql", "/clean-user.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class CatalogControllerTest {

    private static final String PATH_BUSINESSES_TYPE = "/catalog/business_types";
    private static final String PATH_SERVICE_TYPE = "/catalog/service_types";
    private static final String PATH_SERVICE = "/catalog/services";

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BusinessTypeService businessTypeService;

    @Autowired
    private ServiceTypeService serviceTypeService;

    @Autowired
    private ServiceBusinessService serviceBusinessService;

    @Test
    public void findBusinessTypeById_Test() throws Exception {
        //given
        BusinessTypeDto businessTypeDto = businessTypeService.findAll().get(0);
        //when
        mockMvc.perform(get(PATH_BUSINESSES_TYPE+"/{id}", businessTypeDto.getId()))
        //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(businessTypeDto.getName()));
    }

    @Test
    public void findAllBusinessType_Test() throws Exception {
        //given
        List<BusinessTypeDto> all = businessTypeService.findAll();
        //when
        mockMvc.perform(get(PATH_BUSINESSES_TYPE))
                //then
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().json(objectMapper.writeValueAsString(all)));
    }

    @Test
    public void findServiceTypeByBusinessTypeId() throws Exception {
        //given
        BusinessTypeDto businessTypeDto = businessTypeService.findAll().get(0);
        //when
        mockMvc.perform(get(PATH_BUSINESSES_TYPE+"/{id}/service_types", businessTypeDto.getId()))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].businessType.name").value(businessTypeDto.getName()));
    }

    @Test
    public void findServiceTypeById() throws Exception {
        //given
        ServiceTypeDto serviceTypeDto = serviceTypeService.findAll().get(0);
        //when
        mockMvc.perform(get(PATH_SERVICE_TYPE+"/{id}", serviceTypeDto.getId()))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(serviceTypeDto.getName()));
    }

    @Test
    public void findServiceByServiceTypeId() throws Exception {
        //given
        ServiceTypeDto serviceTypeDto = serviceTypeService.findAll().get(0);
        //when
        mockMvc.perform(get(PATH_SERVICE_TYPE+"/{id}/services", serviceTypeDto.getId()))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].serviceType.name").value(serviceTypeDto.getName()));
    }

    @Test
    public void findServiceById() throws Exception {
        //given
        ServiceBusinessDto serviceBusinessDto = serviceBusinessService.findAll().get(0);
        //when
        mockMvc.perform(get(PATH_SERVICE+"/{id}", serviceBusinessDto.getId()))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(serviceBusinessDto.getName()));
    }

    @Test
    public void findAllService() throws Exception {
        //given
        List<ServiceBusinessDto> all = serviceBusinessService.findAll();
        //when
        mockMvc.perform(get(PATH_SERVICE))
                //then
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().json(objectMapper.writeValueAsString(all)));
    }

    @Test
    public void saveService() throws Exception {
        //given
        ServiceTypeDto serviceTypeDto = serviceTypeService.findAll().get(0);
        ServiceBusinessDto newDto = new ServiceBusinessDto();
        newDto.setName("save test");
        newDto.setServiceType(serviceTypeDto);
        //when
        mockMvc.perform(post(PATH_SERVICE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(newDto)))
                //then
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.name").value(newDto.getName()));
    }

    @Test
    public void updateService() throws Exception {
        //given
        ServiceBusinessDto serviceBusinessDto = serviceBusinessService.findAll().get(0);
        serviceBusinessDto.setName("update test");
        //when
        mockMvc.perform(put(PATH_SERVICE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(serviceBusinessDto)))
                //then
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.name").value(serviceBusinessDto.getName()));
    }
}