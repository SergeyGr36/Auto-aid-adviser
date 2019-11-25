package com.hillel.evo.adviser.dto;

import com.hillel.evo.adviser.entity.SimpleUser;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserProfileDto {
    private Long id;
    @NotNull
    private SimpleUser user;
}
