package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.dto.WSInputDTO;
import com.hillel.evo.adviser.dto.WSOutputDTO;
import com.hillel.evo.adviser.exception.UnsupportedSearchTypeException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WebSocketServiceImp implements WebSocketService {

    private final static String BUSINESS_TYPE = "businesstype";
    private final static String SERVICE_TYPE = "servicetype";
    private final static String SERVICE = "service";
    private final static String SERVICE_FOR_BUSINESS = "serviceforbusiness";

    private transient BusinessTypeService businessTypeService;
    private transient ServiceTypeService serviceTypeService;
    private transient ServiceForBusinessService serviceForBusinessService;

    public WSOutputDTO find(WSInputDTO dto) {

        switch (dto.getSearchType().toLowerCase()) {
            case BUSINESS_TYPE:
                return findBusinessTypeByName(dto.getContent());
            case SERVICE_TYPE:
                return findServiceTypeByName(dto.getContent(), dto.getWsInputDTO().orElseGet(() -> new WSInputDTO()).getContent());
            case SERVICE:
            case SERVICE_FOR_BUSINESS:
                return findServiceByName(dto.getContent());
            default:
                throw new UnsupportedSearchTypeException("Please specify correct search type, " + dto.getSearchType()
                        + " is not correct. Supported types are: 'BusinessType' and ServiceType");
        }
    }

    @SuppressWarnings("PMD.UseLocaleWithCaseConversions")
    private WSOutputDTO findBusinessTypeByName(String name) {
        WSOutputDTO result = new WSOutputDTO();
        var businessTypeList = businessTypeService.findAllByNameContains("*" + name.toLowerCase() + "*");
        result.setResult(businessTypeList.stream().map(b -> b.getName()).collect(Collectors.toList()));
        return result;
    }

    @SuppressWarnings("PMD.UseLocaleWithCaseConversions")
    private WSOutputDTO findServiceByName(String name) {
        WSOutputDTO result = new WSOutputDTO();
        var serviceList = serviceForBusinessService.getAllByServiceName("*" + name.toLowerCase() + "*");
        result.setResult(serviceList.stream().map(b -> b.getName()).collect(Collectors.toList()));
        return result;
    }

    @SuppressWarnings("PMD.UseLocaleWithCaseConversions")
    private WSOutputDTO findServiceTypeByName(String name, String btName) {
        WSOutputDTO result = new WSOutputDTO();
        var businessTypeList = Optional.ofNullable(btName).isEmpty() ?
                serviceTypeService.findAllByName("*" + name.toLowerCase() + "*") :
                serviceTypeService.findAllByName("*" + name.toLowerCase() + "*", btName);
        result.setResult(businessTypeList.stream().map(b -> b.getName()).collect(Collectors.toList()));
        return result;
    }
}
