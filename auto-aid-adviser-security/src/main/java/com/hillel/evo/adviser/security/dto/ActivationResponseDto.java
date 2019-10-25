package com.hillel.evo.adviser.security.dto;

import lombok.Data;

@Data
public class ActivationResponseDto {

    Long userId;

    String token;
}
