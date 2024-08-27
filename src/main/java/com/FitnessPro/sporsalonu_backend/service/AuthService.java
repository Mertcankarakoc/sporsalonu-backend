package com.FitnessPro.sporsalonu_backend.service;

import com.FitnessPro.sporsalonu_backend.dto.JwtAuthenticationResponse;
import com.FitnessPro.sporsalonu_backend.dto.SignInRequest;
import com.FitnessPro.sporsalonu_backend.dto.SignUpRequest;
import com.FitnessPro.sporsalonu_backend.model.Enum.Role;
import com.FitnessPro.sporsalonu_backend.model.User;
import com.FitnessPro.sporsalonu_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse signUp(SignUpRequest request) {

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user.getEmail());
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    public JwtAuthenticationResponse signIn(SignInRequest request) {
        try {
            var user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            var jwt = jwtService.generateToken(user.getEmail());
            return JwtAuthenticationResponse.builder().token(jwt).build();
        } catch (DisabledException e) {
            log.error("User is disabled", e);
            throw new IllegalArgumentException("User account is disabled.");
        } catch (Exception e) {
            log.error("Error while signing in", e);
            throw e; // Rethrow the exception to propagate the error
        }
    }
}