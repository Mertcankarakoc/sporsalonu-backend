package com.FitnessPro.sporsalonu_backend.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequest {

    @NonNull
    private String email;
    private String password;
}