package com.FitnessPro.sporsalonu_backend.service;

import com.FitnessPro.sporsalonu_backend.dto.UserCreateRequest;
import com.FitnessPro.sporsalonu_backend.dto.UserUpdateRequest;
import com.FitnessPro.sporsalonu_backend.model.ApiResponse;
import com.FitnessPro.sporsalonu_backend.model.User;
import com.FitnessPro.sporsalonu_backend.model.UserRole;
import com.FitnessPro.sporsalonu_backend.repository.UserRepository;
import com.FitnessPro.sporsalonu_backend.repository.UserRoleRepository;
import com.FitnessPro.sporsalonu_backend.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public ApiResponse deleteUser(UUID id) {
        try {
            var user = userRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            userRepository.delete(user);
            return new ApiResponse(null, "User deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while deleting user", e);
            return new ApiResponse(null, "Error while deleting user", INTERNAL_SERVER_ERROR);
        }
    }


    public ApiResponse getUserById(UUID id) {
        try {
            var user = userRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            return new ApiResponse(user, "User fetched successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while fetching user", e);
            return new ApiResponse(null, "Error while fetching user", INTERNAL_SERVER_ERROR);
        }
    }

    public ApiResponse getAllUsers() {
        try {
            return new ApiResponse(userRepository.findAll(), "Users fetched successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while fetching users", e);
            return new ApiResponse(null, "Error while fetching users", INTERNAL_SERVER_ERROR);
        }
    }

    public ApiResponse createUser(UserCreateRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return new ApiResponse(request, "User already exists", HttpStatus.BAD_REQUEST);
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // Encode the password
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .birthDay(request.getBirthDay()) // Ensure birthDay is set correctly
                .isActive(true)
                .build();

        userRepository.save(user);
        return new ApiResponse(user, "User created successfully", HttpStatus.OK);
    }

    public ApiResponse updateProfile(UserUpdateRequest userUpdateRequest, String authHeader) {
        try {
            // Authorization header'dan JWT token'ı al
            String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;

            // Token'dan kullanıcı ID'sini çıkar
            String userIdStr = jwtService.extractUserId(token);
            UUID userId = UUID.fromString(userIdStr);

            // Kullanıcıyı ID ile bul
            var user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            // Kullanıcı bilgilerini güncelle
            user.setFirstName(userUpdateRequest.getFirstName());
            user.setLastName(userUpdateRequest.getLastName());
            user.setEmail(userUpdateRequest.getEmail());
            user.setPassword(passwordEncoder.encode(userUpdateRequest.getPassword())); // Şifreyi encode edin
            user.setAddress(userUpdateRequest.getAddress());
            user.setPhoneNumber(userUpdateRequest.getPhoneNumber());
            user.setBirthDay(userUpdateRequest.getBirthDay());

            // Kullanıcıyı kaydet
            userRepository.save(user);

            // Başarılı yanıt döndür
            return new ApiResponse(user, "User updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while updating user", e);
            return new ApiResponse(null, "Error while updating user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
