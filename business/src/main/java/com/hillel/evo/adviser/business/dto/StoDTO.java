package com.hillel.evo.adviser.business.dto;

import com.hillel.evo.adviser.business.entity.*;
import lombok.Data;

import java.util.List;
@Data
public class StoDTO extends BusinessDTO {
    private List<ServicesForSTO> servicesForSTO;
    private List<MotorType> motorType;
    private List<Transmission> transmission;
    private List<CarBrand> carBrands;
    private List<CarType> carTypes;
}
