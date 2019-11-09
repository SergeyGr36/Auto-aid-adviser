package com.hillel.evo.adviser.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = {"id"})
public class Image {
    @Id
    private Long id;

    private String imageId;

    private String originalFileName;
}
