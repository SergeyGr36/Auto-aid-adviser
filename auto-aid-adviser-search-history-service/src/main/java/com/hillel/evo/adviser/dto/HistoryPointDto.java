package com.hillel.evo.adviser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HistoryPointDto {
    private Long id;
    @NotNull
    private Long userId;
    @NotNull
    List<BusinessShortDto> businessDto;
    @NotNull
    private LocalDateTime searchDate;

    public HistoryPointDto(@NotNull Long userId, @NotNull List<BusinessShortDto> businessDto, @NotNull LocalDateTime searchDate) {
        this.userId = userId;
        this.searchDate = searchDate;
        this.businessDto = businessDto;
    }
}
