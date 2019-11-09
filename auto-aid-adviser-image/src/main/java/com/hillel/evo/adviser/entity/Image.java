package com.hillel.evo.adviser.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = {"id"})
public class Image {
    @Id
    private Long id;
    @NotNull
    private String keyFileName;

    public Image(@NotNull String keyFileName) {
        this.keyFileName = keyFileName;
    }
}
