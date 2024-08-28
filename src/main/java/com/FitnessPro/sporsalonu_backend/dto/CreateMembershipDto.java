package com.FitnessPro.sporsalonu_backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateMembershipDto {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    private int totalDays;
    private String status;
    private boolean isActive;
}