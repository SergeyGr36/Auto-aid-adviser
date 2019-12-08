package com.hillel.evo.adviser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HistoryPointDto {
    private Long id;
    @NotNull
    private Long userId;
    @NotNull
    private LocalDateTime searchDate;
    @NotNull
    private HistoryLocationDto location;
    @NotNull
    private Long serviceId;

    public HistoryPointDto(@NotNull Long userId, @NotNull LocalDateTime searchDate, @NotNull HistoryLocationDto location, @NotNull Long serviceId) {
        this.userId = userId;
        this.searchDate = searchDate;
        this.location = location;
        this.serviceId = serviceId;
    }
}
