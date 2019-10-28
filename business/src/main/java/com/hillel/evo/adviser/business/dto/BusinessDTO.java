package com.hillel.evo.adviser.business.dto;

import lombok.Data;

import javax.validation.constraints.Positive;
import java.util.List;

@Data
public class BusinessDTO {
private long id;
private String name;
    @Positive
    private double longitude;
    @Positive
    private double latitude;
    private String contactPhone;
    private String workingDays;
    private String workingHours;
    private String businessType; //питання на рахунок типу(строка чи екземпляр класу)
    private List<String> services; //в залежності від типу. В сервісі через if провірить
}
