package com.FitnessPro.sporsalonu_backend.service.jwt;

import com.FitnessPro.sporsalonu_backend.dto.JwtAuthenticationResponse;
import com.FitnessPro.sporsalonu_backend.dto.SignInRequest;
import com.FitnessPro.sporsalonu_backend.dto.SignUpRequest;
import com.FitnessPro.sporsalonu_backend.dto.UserResponse;
import com.FitnessPro.sporsalonu_backend.model.User;
import com.FitnessPro.sporsalonu_backend.model.UserRole;
import com.FitnessPro.sporsalonu_backend.repository.UserRepository;
import com.FitnessPro.sporsalonu_backend.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRoleRepository userRoleRepository;


    public UserResponse signUp(SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new RuntimeException("Email already exists..!!");
        }

        User user = new User();
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        Set<UserRole> roles = new HashSet<>();
        for (String role : signUpRequest.getRoles()) {
            UserRole userRole = userRoleRepository.findByName(role.toUpperCase())
                    .orElseGet(() -> userRoleRepository.save(new UserRole(role.toUpperCase())));
            roles.add(userRole);
        }
        user.setRoles(roles);

        user = userRepository.save(user);
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .roles(user.getRoles())
                .build();
    }

    public JwtAuthenticationResponse signIn(SignInRequest signInRequest) {
        try {
            // Kullanıcıyı doğrulama
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));

            // Kullanıcı kimlik doğrulaması başarılı ise
            if (authentication.isAuthenticated()) {
                // Kullanıcı e-posta adresi ile kullanıcıyı bul
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                String email = userDetails.getUsername();

                // Kullanıcıyı e-posta ile bul ve ID'sini al
                User user = userRepository.findByEmail(email);

                // Token oluştur
                String token = jwtService.generateToken(email, user.getId());

                // Token'ı yanıt olarak döndür
                return JwtAuthenticationResponse.builder()
                        .accessToken(token)
                        .build();
            } else {
                throw new UsernameNotFoundException("Invalid user request.");
            }
        } catch (Exception e) {
            log.error("Error during sign-in", e);
            throw new RuntimeException("Error during sign-in. Please try again.");
        }
    }

}