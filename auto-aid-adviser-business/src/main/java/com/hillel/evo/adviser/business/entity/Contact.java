package com.hillel.evo.adviser.business.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class Contact {
    private String phone;
//    private String name;
}
