package com.hillel.evo.adviser;

import com.hillel.evo.adviser.configuration.HibernateSearchConfig;
import com.hillel.evo.adviser.dto.WSInputDTO;
import com.hillel.evo.adviser.entity.BusinessType;
import com.hillel.evo.adviser.entity.ServiceType;
import com.hillel.evo.adviser.exception.UnsupportedSearchTypeException;
import com.hillel.evo.adviser.service.WebSocketService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = {WebSocketApp.class})
@Sql(value = {"/clean-business.sql", "/create-business.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class WebSocketServiceImpTest extends BaseTest {

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private HibernateSearchConfig config;

    @Test
    public void whenSearchBisnessTypeThenReturnList() {
        config.reindex(BusinessType.class);
        var wsInputDTO = new WSInputDTO();
        wsInputDTO.setSearchType("BusinessType");
        wsInputDTO.setContent("sh");

        var result = webSocketService.find(wsInputDTO);

        assertEquals(2, result.getResult().size());

    }

    @Test
    public void whenSearchUABisnessTypeThenReturnList() {
        config.reindex(BusinessType.class);
        var wsInputDTO = new WSInputDTO();
        wsInputDTO.setSearchType("BusinessType");
        wsInputDTO.setContent("магаз");

        var result = webSocketService.find(wsInputDTO);

        assertEquals(2, result.getResult().size());

    }

    @Test
    public void whenSearchServiseTypeThenReturnList() {

        config.reindex(ServiceType.class);
        var btInputDTO = new WSInputDTO();
        btInputDTO.setSearchType("BusinessType");
        btInputDTO.setContent("CTO");
        var wsInputDTO = new WSInputDTO();
        wsInputDTO.setSearchType("ServiceType");
        wsInputDTO.setContent("ru");
        wsInputDTO.setInputDTO(btInputDTO);

        var result = webSocketService.find(wsInputDTO);

        assertEquals(1, result.getResult().size());
    }

    @Test
    public void whenSearchUAServiseTypeThenReturnList() {

        config.reindex(ServiceType.class);
        var btInputDTO = new WSInputDTO();
        btInputDTO.setSearchType("BusinessType");
        btInputDTO.setContent("шиномонтаж");
        var wsInputDTO = new WSInputDTO();
        wsInputDTO.setSearchType("ServiceType");
        wsInputDTO.setContent("дв");
        wsInputDTO.setInputDTO(btInputDTO);

        var result = webSocketService.find(wsInputDTO);

        assertEquals(1, result.getResult().size());
    }

    @Test
    public void whenSearchUnsupportedTypeThenThrowsException() {

        var wsInputDTO = new WSInputDTO();
        wsInputDTO.setSearchType("SomeType");
        wsInputDTO.setContent("wee");

        assertThrows(UnsupportedSearchTypeException.class,
                () -> webSocketService.find(wsInputDTO));

    }



}
