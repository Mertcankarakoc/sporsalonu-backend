package com.FitnessPro.sporsalonu_backend.dto;

import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class UpdateMembershipDto {

    private LocalDate startDate;
    private int totalDays;
    private int remainingDays;
    private String status;
    @UpdateTimestamp
    private Date updatedAt;
    private boolean isActive;
}
