package com.FitnessPro.sporsalonu_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ApiResponse {

    private Object data;
    private String message;
    private HttpStatus httpStatus;
}