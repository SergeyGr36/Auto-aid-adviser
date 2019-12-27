package com.hillel.evo.adviser.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class FeedbackDto {
    private Long id;
    @NotEmpty
    private String text;
    @Max(5)
    private int rating;
    private Long userId;
    private LocalDateTime createDate;
}
