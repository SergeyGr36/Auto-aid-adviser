package com.hillel.evo.advisor.business.entity;

import lombok.Data;

import javax.persistence.Entity;
import java.util.List;
@Data
@Entity
public class BusinessUser {
//    user details
private List<Business> business;
}
