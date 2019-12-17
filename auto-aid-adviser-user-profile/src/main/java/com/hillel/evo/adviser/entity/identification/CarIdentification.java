package com.hillel.evo.adviser.entity.identification;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @param <T> Type parent element for field "parent"
 */
@Data
@Entity(name = "car_identification")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_ident")
@EqualsAndHashCode(of = {"id"})
public abstract class CarIdentification<T extends CarIdentification> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private T parent;

    @NotNull
    private String name;
}
