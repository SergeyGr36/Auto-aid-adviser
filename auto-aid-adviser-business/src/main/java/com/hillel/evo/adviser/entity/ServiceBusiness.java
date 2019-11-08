package com.hillel.evo.adviser.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "service")
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
//Есть справочник услуг (покраска, замена масла, продажа запчастей, ...) услуги привязаны к группам услуг
public class ServiceBusiness {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private ServiceType serviceType;
}
