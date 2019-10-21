package com.hillel.evo.adviser.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "business_usr")
@NoArgsConstructor
@Data
public class BusinessUser {
    @Id
    private Long id;
    @OneToOne
    @MapsId
    private AdviserUserDetails userDetails;
}
