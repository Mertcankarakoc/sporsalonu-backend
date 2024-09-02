package com.FitnessPro.sporsalonu_backend.dto;

import com.FitnessPro.sporsalonu_backend.model.Address;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class UserCreateRequest {

    private String firstName;
    private String lastName;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private String phoneNumber;
    private Address address;
    private LocalDateTime birthDay;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
