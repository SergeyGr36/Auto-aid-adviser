package com.hillel.evo.adviser.dto;

import lombok.Data;

@Data
public class WSInputDTO {

    private String searchType;
    private String content;
    private WSInputDTO inputDTO;

    @Override
    public String toString() {
        return "{" +
                "\"searchType\": \"" + searchType + "\"" +
                ", \"content\": \"" + content + "\"" +
                ", \"inputDTO\": " + inputDTO +
                '}';
    }
}
