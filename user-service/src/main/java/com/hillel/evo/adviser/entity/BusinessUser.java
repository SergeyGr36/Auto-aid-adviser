package com.hillel.evo.adviser.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "business_usr")
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = {"id"})
public class BusinessUser {
    @Id
    private Long id;
    @OneToOne
    @MapsId
    private AdviserUserDetails userDetails;
}
