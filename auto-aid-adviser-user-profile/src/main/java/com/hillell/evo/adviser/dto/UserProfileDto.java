package com.hillell.evo.adviser.dto;

import com.hillel.evo.adviser.entity.ServiceForBusiness;
import com.hillel.evo.adviser.entity.SimpleUser;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserProfileDto {
    private Long id;
    @NotNull
    private SimpleUser user;
    private ServiceForBusiness typeCar;
    private ServiceForBusiness carBrand;
    private ServiceForBusiness motorType;
}
