package com.hillel.evo.advisor.business.entity;

import lombok.Data;

import javax.persistence.Entity;
@Data
@Entity
public class Business {
    private BusinessType businessType;
}
