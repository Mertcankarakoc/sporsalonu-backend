package com.FitnessPro.sporsalonu_backend.dto;

import com.FitnessPro.sporsalonu_backend.model.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<String> roles;

}
