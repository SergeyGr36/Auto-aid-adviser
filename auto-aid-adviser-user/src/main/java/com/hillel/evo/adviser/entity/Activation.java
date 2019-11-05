package com.hillel.evo.adviser.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Data
@Embeddable
public class Activation {
    private String code;
    private LocalDateTime dateCreate;
    private int expiration;
}
