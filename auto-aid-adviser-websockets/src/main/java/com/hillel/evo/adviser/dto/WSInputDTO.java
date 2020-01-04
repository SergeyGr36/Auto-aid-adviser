package com.hillel.evo.adviser.dto;

import lombok.Data;

import java.util.Optional;

@Data
public class WSInputDTO {

    private String searchType;
    private String content;
    private WSInputDTO inputDTO;

    public Optional<WSInputDTO> getWsInputDTO() {
        return Optional.ofNullable(inputDTO);
    }

    @Override
    public String toString() {
        return "{" +
                "\"searchType\": \"" + searchType + "\"" +
                ", \"content\": \"" + content + "\"" +
                ", \"inputDTO\": " + inputDTO +
                '}';
    }
}
