package com.FitnessPro.sporsalonu_backend.dto;

import com.FitnessPro.sporsalonu_backend.model.Membership;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MembershipProfileDto {

    private UUID id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    private int totalDays;
    private int remainingDays;
    private String status;
    private String membershipType;

    public MembershipProfileDto(Membership membership) {
    }
}
