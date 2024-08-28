package com.FitnessPro.sporsalonu_backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Membership {

    @Id
    @GeneratedValue
    private UUID id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    private int totalDays;
    private int remainingDays;
    private String status;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    private boolean isActive;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "user_id")
    private User user;

    public int calculateRemainingDays() {
        LocalDate today = LocalDate.now();
        int daysPassed = (int) java.time.temporal.ChronoUnit.DAYS.between(this.startDate, today);
        return Math.max(totalDays - daysPassed, 0);
    }

    @PrePersist
    @PreUpdate
    public void updateRemainingDays() {
        this.remainingDays = calculateRemainingDays();
        this.status = this.remainingDays > 0 ? "ACTIVE" : "EXPIRED";
    }
}