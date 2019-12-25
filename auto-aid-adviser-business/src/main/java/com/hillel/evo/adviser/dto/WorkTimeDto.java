package com.hillel.evo.adviser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkTimeDto {
    private Long id;
    @Min(1) @Max(7)
    private Integer day;
    @NotEmpty
    private LocalTime fromTime;
    @NotEmpty
    private LocalTime toTime;

    public WorkTimeDto(@Min(1) @Max(7) Integer day, @NotEmpty LocalTime fromTime, @NotEmpty LocalTime toTime) {
        this.day = day;
        this.fromTime = fromTime;
        this.toTime = toTime;
    }
}
