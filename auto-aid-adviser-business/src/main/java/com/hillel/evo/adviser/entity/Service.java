package com.hillel.evo.adviser.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

/*
@Entity
@Table(name = "service_tree")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
*/
public class Service {
/*

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Service parent;

    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    private Set<Service> children;
*/
}
