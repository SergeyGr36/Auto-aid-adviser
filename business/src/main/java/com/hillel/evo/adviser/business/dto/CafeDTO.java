package com.hillel.evo.adviser.business.dto;

import com.hillel.evo.adviser.business.entity.CuisinesType;
import com.hillel.evo.adviser.business.entity.ServicesForCafe;
import lombok.Data;

import java.util.List;

@Data
public class CafeDTO extends BusinessDTO{
    private List<ServicesForCafe> servicesForCafe;
    private List<CuisinesType>cuisinesTypes;
}
