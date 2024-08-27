package com.FitnessPro.sporsalonu_backend.controller;

import com.FitnessPro.sporsalonu_backend.dto.JwtAuthenticationResponse;
import com.FitnessPro.sporsalonu_backend.dto.SignInRequest;
import com.FitnessPro.sporsalonu_backend.dto.SignUpRequest;
import com.FitnessPro.sporsalonu_backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public JwtAuthenticationResponse signUp(@RequestBody SignUpRequest request) {
        return authService.signUp(request);
    }

    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody SignInRequest request) {
        return authService.signIn(request);
    }
}
