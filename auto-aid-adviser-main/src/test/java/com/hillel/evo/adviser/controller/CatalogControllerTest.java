package com.hillel.evo.adviser.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillel.evo.adviser.AdviserStarter;
import com.hillel.evo.adviser.dto.BusinessTypeDto;
import com.hillel.evo.adviser.dto.ServiceForBusinessDto;
import com.hillel.evo.adviser.dto.ServiceTypeDto;
import com.hillel.evo.adviser.entity.BusinessType;
import com.hillel.evo.adviser.repository.BusinessTypeRepository;
import com.hillel.evo.adviser.service.BusinessTypeService;
import com.hillel.evo.adviser.service.ServiceForBusinessService;
import com.hillel.evo.adviser.service.ServiceTypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
    private ServiceForBusinessService serviceForBusinessService;

    @Autowired
    private BusinessTypeRepository businessTypeRepository;

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
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(all)))
                ;
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
        ServiceTypeDto serviceTypeDto = serviceTypeService.findAllByPages(0, 2).getContent().get(0);
        //when
        mockMvc.perform(get(PATH_SERVICE_TYPE+"/{id}", serviceTypeDto.getId()))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(serviceTypeDto.getName()));
    }

    @Test
    public void findServiceByServiceTypeId() throws Exception {
        //given
        ServiceTypeDto serviceTypeDto = serviceTypeService.findAllByPages(0, 2).getContent().get(0);
        //when
        mockMvc.perform(get(PATH_SERVICE_TYPE+"/{id}/services", serviceTypeDto.getId()))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].serviceType.name").value(serviceTypeDto.getName()));
    }

    @Test
    public void findServiceById() throws Exception {
        //given
        ServiceForBusinessDto serviceForBusinessDto = serviceForBusinessService.byPages(0, 2).getContent().get(0);
        //when
        mockMvc.perform(get(PATH_SERVICE+"/{id}", serviceForBusinessDto.getId()))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(serviceForBusinessDto.getName()));
    }

    @Test
    public void serviceByPages_Default() throws Exception {
        mockMvc.perform(get(PATH_SERVICE))
                //then
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.number").value(0))
                .andExpect(jsonPath("$.size").value(50))
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    public void serviceByPages_WithParams() throws Exception {
        //given
        final Integer page = 1;
        final Integer size = 5;
        //when
        mockMvc.perform(get(PATH_SERVICE+"?page="+page+"&size="+size))
                //then
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.number").value(page))
                .andExpect(jsonPath("$.size").value(size))
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    public void serviceByPages_WithBadParams() throws Exception {
        //given
        final Integer size = 500;
        //when
        mockMvc.perform(get(PATH_SERVICE+"?size="+size))
                //then
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void saveService() throws Exception {
        //given
        ServiceTypeDto serviceTypeDto = serviceTypeService.findAllByPages(0, 2).getContent().get(0);
        ServiceForBusinessDto newDto = new ServiceForBusinessDto();
        newDto.setName("save test");
        newDto.setServiceType(serviceTypeDto);
        //when
        mockMvc.perform(post(PATH_SERVICE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(newDto)))
                //then
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name").value(newDto.getName()));
    }

    @Test
    public void updateService() throws Exception {
        //given
        ServiceForBusinessDto serviceForBusinessDto = serviceForBusinessService.byPages(0, 2).getContent().get(0);
        serviceForBusinessDto.setName("update test");
        //when
        mockMvc.perform(put(PATH_SERVICE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(serviceForBusinessDto)))
                //then
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name").value(serviceForBusinessDto.getName()));
    }

    @Test
    public void saveBusinessType() throws Exception {
        //given
        BusinessTypeDto newBusiness = new BusinessTypeDto();
        newBusiness.setName("new business type");
        //when
        mockMvc.perform(post(PATH_BUSINESSES_TYPE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newBusiness)))
                //then
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name").value(newBusiness.getName()))
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    public void updateBusinessType() throws Exception {
        //given
        BusinessTypeDto newBusiness = businessTypeService.findAll().get(0);
        newBusiness.setName("new business type");
        //when
        mockMvc.perform(put(PATH_BUSINESSES_TYPE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newBusiness)))
                //then
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name").value(newBusiness.getName()))
                .andExpect(jsonPath("$.id").value(newBusiness.getId()));
    }

    @Test
    public void deleteBusinessType() throws Exception {
        //given
        BusinessType type = businessTypeRepository.findByName("test").get();
        //when
        mockMvc.perform(delete(PATH_BUSINESSES_TYPE+"/{id}", type.getId()))
                //then
                .andExpect(status().isOk());
    }


}