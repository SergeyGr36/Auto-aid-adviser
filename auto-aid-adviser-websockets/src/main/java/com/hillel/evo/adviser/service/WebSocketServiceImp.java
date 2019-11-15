package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.dto.WSInputDTO;
import com.hillel.evo.adviser.dto.WSOutputDTO;
import com.hillel.evo.adviser.exception.UnsupportedSearchTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        //WSOutputDTO result = new WSOutputDTO();
        switch (dto.getSearchType()) {
            case BUSINESS_TYPE:
                var resultb = new WSOutputDTO();
                var businessTypeList = businessTypeService.findAllByName(dto.getContent());
                resultb.setResult(businessTypeList.stream().map(b -> b.getName()).collect(Collectors.toList()));
                return resultb;
//                break;
            case SERVICE_TYPE:
                var results = new WSOutputDTO();
                var serviceTypeList = serviceTypeService.findAllByName(dto.getContent());
                results.setResult(serviceTypeList.stream().map(b -> b.getName()).collect(Collectors.toList()));
                return results;
//                break;
            default:
                throw new UnsupportedSearchTypeException("Please specify correct search type, " + dto.getSearchType()
                        + " is not correct. Supported types are: 'BusinessType' and ServiceType");

        }

//        return null;
    }

}
