package com.hillel.evo.adviser.entity;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class Contact {
    private String phone;
    private String url;
}
