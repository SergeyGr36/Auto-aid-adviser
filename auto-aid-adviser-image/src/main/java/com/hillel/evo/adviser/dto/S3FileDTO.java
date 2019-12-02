package com.hillel.evo.adviser.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Getter
public class S3FileDTO {

    private MultipartFile file;
    private String uniqFileName;
    private String keyFileName;

}
