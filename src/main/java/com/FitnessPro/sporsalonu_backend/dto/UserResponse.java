package com.FitnessPro.sporsalonu_backend.dto;

import com.FitnessPro.sporsalonu_backend.model.UserRole;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class UserResponse {
    private UUID id; // Ensure the type matches the User entity
    private String firstName;
    private String lastName;
    private String email;
    private Set<UserRole> roles;
}