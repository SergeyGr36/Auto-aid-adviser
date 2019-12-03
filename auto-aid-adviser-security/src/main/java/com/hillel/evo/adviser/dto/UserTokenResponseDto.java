package com.hillel.evo.adviser.dto;

import com.hillel.evo.adviser.enums.RoleUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTokenResponseDto {

    private Long id;

    private String email;

    private RoleUser role;

    private String token;
}
