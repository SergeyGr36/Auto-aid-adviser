package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.dto.WSInputDTO;
import com.hillel.evo.adviser.dto.WSOutputDTO;
import com.hillel.evo.adviser.exception.UnsupportedSearchTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class WebSocketServiceImp implements WebSocketService {

    private final static String BUSINESS_TYPE = "BusinessType";
    private final static String SERVICE_TYPE = "ServiceType";

    private transient BusinessTypeService businessTypeService;
    private transient ServiceTypeService serviceTypeService;

    @Autowired
    public void setBusinessTypeService(BusinessTypeService businessTypeService) {
        this.businessTypeService = businessTypeService;
    }

    @Autowired
    public void setServiceType(ServiceTypeService serviceTypeService) {
        this.serviceTypeService = serviceTypeService;
    }

    public WSOutputDTO find(WSInputDTO dto) {

        switch (dto.getSearchType()) {
            case BUSINESS_TYPE:
                return findBusinessTypeByName(dto.getContent());
            case SERVICE_TYPE:
                return findServiceTypeByName(dto.getContent(), dto.getInputDTO().getContent());
            default:
                throw new UnsupportedSearchTypeException("Please specify correct search type, " + dto.getSearchType()
                        + " is not correct. Supported types are: 'BusinessType' and ServiceType");
        }
    }

    private WSOutputDTO findBusinessTypeByName(String name) {
        WSOutputDTO result = new WSOutputDTO();
        var businessTypeList = businessTypeService.findAllByNameContains("*" + name.toLowerCase(new Locale("ru", "RU")) + "*");
        result.setResult(businessTypeList.stream().map(b -> b.getName()).collect(Collectors.toList()));
        return result;
    }

    private WSOutputDTO findServiceTypeByName(String name, String btName) {
        WSOutputDTO result = new WSOutputDTO();
        var businessTypeList = serviceTypeService.findAllByNameContains(
                "*" + name.toLowerCase(new Locale("ru", "RU")) + "*", btName
                );
        result.setResult(businessTypeList.stream().map(b -> b.getName()).collect(Collectors.toList()));
        return result;
    }
}
