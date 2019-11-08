package com.hillel.evo.adviser.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = {"id"})
public class Image {
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private BusinessUser businessUser;

    private String imageId;
}
