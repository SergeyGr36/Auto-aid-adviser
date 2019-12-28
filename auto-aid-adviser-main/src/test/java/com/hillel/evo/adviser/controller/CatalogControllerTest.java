package com.hillel.evo.adviser.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillel.evo.adviser.AdviserStarter;
import com.hillel.evo.adviser.configuration.HibernateSearchConfig;
import com.hillel.evo.adviser.dto.BusinessTypeDto;
import com.hillel.evo.adviser.dto.ServiceForBusinessDto;
import com.hillel.evo.adviser.dto.ServiceTypeDto;
import com.hillel.evo.adviser.entity.BusinessType;
import com.hillel.evo.adviser.entity.CarBrand;
import com.hillel.evo.adviser.entity.CarModel;
import com.hillel.evo.adviser.entity.ServiceType;
import com.hillel.evo.adviser.entity.TypeCar;
import com.hillel.evo.adviser.repository.BusinessTypeRepository;
import com.hillel.evo.adviser.repository.CarBrandRepository;
import com.hillel.evo.adviser.repository.CarModelRepository;
import com.hillel.evo.adviser.repository.TypeCarRepository;
import com.hillel.evo.adviser.service.BusinessTypeService;
import com.hillel.evo.adviser.service.ServiceForBusinessService;
import com.hillel.evo.adviser.service.ServiceTypeService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AdviserStarter.class)
@AutoConfigureMockMvc
@Sql(value = {"/clean-user-profile.sql", "/clean-business.sql", "/clean-user.sql",
        "/create-user2.sql", "/create-business.sql", "/user-profile.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/clean-user-profile.sql", "/clean-business.sql", "/clean-user.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class CatalogControllerTest {

    private static final String PATH_BUSINESSES_TYPE = "/catalog/business/type";
    private static final String PATH_SERVICE_TYPE = "/catalog/service/type";
    private static final String PATH_SERVICE = "/catalog/services";
    private static final String PATH_SERVICE_TYPE_CAR = "/catalog/car/type";
    private static final String PATH_SERVICE_CAR_BRAND = "/catalog/car/brand";
    private static final String PATH_SERVICE_CAR_MODEL = "/catalog/car/model";

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

    @Autowired
    private HibernateSearchConfig hibernateSearchConfig;

    @Autowired
    private TypeCarRepository typeCarRepository;

    @Autowired
    private CarBrandRepository carBrandRepository;

    @Autowired
    private CarModelRepository carModelRepository;

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
        mockMvc.perform(get(PATH_BUSINESSES_TYPE+"s"))
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
        mockMvc.perform(get(PATH_BUSINESSES_TYPE+"/{id}/service/types", businessTypeDto.getId()))
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
    @Disabled
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

    @Test
    public void saveServiceType() throws Exception {
        //given
        BusinessTypeDto businessTypeDto =  businessTypeService.findAll().get(0);
        ServiceTypeDto serviceTypeDto = new ServiceTypeDto("test2", businessTypeDto);
        //when
        mockMvc.perform(post(PATH_SERVICE_TYPE)
                .content(objectMapper.writeValueAsString(serviceTypeDto))
                .contentType(MediaType.APPLICATION_JSON))
                //then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(serviceTypeDto.getName()));
    }

    @Test
    public void updateServiceType() throws Exception {
        //given
        hibernateSearchConfig.reindex(ServiceType.class);
        ServiceTypeDto serviceTypeDto = serviceTypeService.findAllByName("test").get(0);
        serviceTypeDto.setName("newName");
        //when
        mockMvc.perform(put(PATH_SERVICE_TYPE)
                .content(objectMapper.writeValueAsString(serviceTypeDto))
                .contentType(MediaType.APPLICATION_JSON))
                //then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(serviceTypeDto.getName()));
    }

    @Test
    public void deleteServiceType() throws Exception {
        //given
        hibernateSearchConfig.reindex(ServiceType.class);
        ServiceTypeDto serviceTypeDto = serviceTypeService.findAllByName("test").get(0);
        //when
        mockMvc.perform(delete(PATH_SERVICE_TYPE+"/{id}", serviceTypeDto.getId()))
                //then
                .andExpect(status().isOk());
    }

    /* *********** Type Car ************ */

    @Test
    public void getAllTypeCar() throws Exception {
        //when
        mockMvc.perform(get(PATH_SERVICE_TYPE_CAR+"s"))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").isString());
    }

    @Test
    public void getTypeCarById() throws Exception {
        //given
        TypeCar typeCar = typeCarRepository.findByName("coupe").get();
        //when
        mockMvc.perform(get(PATH_SERVICE_TYPE_CAR+"/{id}", typeCar.getId()))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(typeCar.getName()));
    }

    @Test
    public void getTypeCarByIdReturnCode404() throws Exception {
        //when
        mockMvc.perform(get(PATH_SERVICE_TYPE_CAR+"/{id}", 999L))
                //then
                .andExpect(status().isNotFound());
    }

    @Test
    public void getTypeCarByName() throws Exception {
        //given
        TypeCar typeCar = typeCarRepository.findByName("coupe").get();
        //when
        mockMvc.perform(get(PATH_SERVICE_TYPE_CAR+"/name/{name}", typeCar.getName()))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(typeCar.getName()));
    }

    @Test
    public void getTypeCarByNameReturnCode404() throws Exception {
        //when
        mockMvc.perform(get(PATH_SERVICE_TYPE_CAR+"/name/{name}", "errName"))
                //then
                .andExpect(status().isNotFound());
    }

    /* *********** Car Brand ************ */

    @Test
    public void getAllCarBrands() throws Exception {
        //when
        mockMvc.perform(get(PATH_SERVICE_CAR_BRAND+"s"))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").isString());
    }

    @Test
    public void getCarBrandById() throws Exception {
        //given
        CarBrand carBrand = carBrandRepository.findByName("BMW").get();
        //when
        mockMvc.perform(get(PATH_SERVICE_CAR_BRAND+"/{id}", carBrand.getId()))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(carBrand.getName()));
    }

    @Test
    public void getCarBrandByIdReturnCode404() throws Exception {
        //when
        mockMvc.perform(get(PATH_SERVICE_CAR_BRAND+"/{id}", 999L))
                //then
                .andExpect(status().isNotFound());
    }

    @Test
    public void getCarBrandByName() throws Exception {
        //given
        CarBrand carBrand = carBrandRepository.findByName("BMW").get();
        //when
        mockMvc.perform(get(PATH_SERVICE_CAR_BRAND+"/name/{name}", carBrand.getName()))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(carBrand.getName()));
    }

    @Test
    public void getCarBrandByNameReturnCode404() throws Exception {
        //when
        mockMvc.perform(get(PATH_SERVICE_CAR_BRAND+"/name/{name}", "errName"))
                //then
                .andExpect(status().isNotFound());
    }

    /* *********** Car Model ************ */

    @Test
    public void getCarModelByIdThenReturnDto() throws Exception {
        //given
        CarModel carModel = carModelRepository.findAll().get(0);
        //when
        mockMvc.perform(get(PATH_SERVICE_CAR_MODEL+"/{id}", carModel.getId()))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(carModel.getName()));
    }

    @Test
    public void getListCarModelByTypeIdAndBrandIdVariable() throws Exception {
        //given
        TypeCar typeCar = typeCarRepository.findByName("crossover").get();
        CarBrand carBrand = carBrandRepository.findByName("BMW").get();
        //when
        mockMvc.perform(get(PATH_SERVICE_CAR_MODEL+"/type/{typeId}/brand/{brandId}", typeCar.getId(), carBrand.getId()))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").isString());
    }

    @Test
    public void getListCarModelByTypeNameAndBrandNameParam() throws Exception {
        //given
        TypeCar typeCar = typeCarRepository.findByName("crossover").get();
        CarBrand carBrand = carBrandRepository.findByName("BMW").get();
        //when
        mockMvc.perform(get(PATH_SERVICE_CAR_MODEL+"s?typeName={type}&brandName={brand}", typeCar.getName(), carBrand.getName()))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").isString());
    }

    @Test
    public void getListCarModelByTypeAndBrandParamThenReturnBadRequest() throws Exception {
        //when
        mockMvc.perform(get(PATH_SERVICE_CAR_MODEL+"s"))
                //then
                .andExpect(status().isBadRequest());
    }
}