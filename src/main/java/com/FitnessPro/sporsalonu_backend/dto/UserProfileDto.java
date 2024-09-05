package com.FitnessPro.sporsalonu_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;


}
