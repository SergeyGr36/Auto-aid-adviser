package com.hillel.evo.adviser.userprofile.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "simple_usr")
@NoArgsConstructor
@Data
public class SimpleUser {
    @Id
    private Long id;
    @OneToOne
    @MapsId
    private AdviserUserDetails userDetails;

    private String firstName;

    private String lastName;
}
