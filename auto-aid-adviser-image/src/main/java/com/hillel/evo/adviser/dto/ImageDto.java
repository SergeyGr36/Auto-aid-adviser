package com.hillel.evo.adviser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.net.URL;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {
    private Long id;

    @NotNull
    private String keyFileName;

    private String originalFileName;

    private URL urlImage;
}
