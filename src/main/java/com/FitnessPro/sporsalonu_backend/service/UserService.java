package com.FitnessPro.sporsalonu_backend.service;

import com.FitnessPro.sporsalonu_backend.dto.UserCreateRequest;
import com.FitnessPro.sporsalonu_backend.dto.UserUpdateRequest;
import com.FitnessPro.sporsalonu_backend.model.ApiResponse;
import com.FitnessPro.sporsalonu_backend.model.Enum.Role;
import com.FitnessPro.sporsalonu_backend.model.User;
import com.FitnessPro.sporsalonu_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ApiResponse deleteUser(UUID id) {
        try {
            userRepository.deleteById(id);
            return new ApiResponse(null, "User deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while deleting user", e);
            return new ApiResponse(null, "Error while deleting user", HttpStatus.INTERNAL_SERVER_ERROR);
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

    public ApiResponse updateUser(UserUpdateRequest userUpdateRequest) {
        try {
            var user = userRepository.findById(userUpdateRequest.getId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            user.setFirstName(userUpdateRequest.getFirstName());
            user.setLastName(userUpdateRequest.getLastName());
            user.setEmail(userUpdateRequest.getEmail());
            user.setPassword(userUpdateRequest.getPassword());
            user.setAddress(userUpdateRequest.getAddress());
            user.setPhoneNumber(userUpdateRequest.getPhoneNumber());
            user.setBirthDay(userUpdateRequest.getBirthDay());
            userRepository.save(user);
            return new ApiResponse(user, "User updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while updating user", e);
            return new ApiResponse(null, "Error while updating user", INTERNAL_SERVER_ERROR);
        }
    }

    public ApiResponse getUserByEmail(String email) {
        try {
            var user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            return new ApiResponse(user, "User fetched successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while fetching user", e);
            return new ApiResponse(null, "Error while fetching user", INTERNAL_SERVER_ERROR);
        }
    }

    public ApiResponse createUser(UserCreateRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
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
                .role(Role.ROLE_ADMIN) // Set the role from the request
                .isActive(true) // Set default active status
                .createdAt(LocalDateTime.now()) // Set createdAt to current time
                .updatedAt(LocalDateTime.now()) // Set updatedAt to current time
                .build();

        userRepository.save(user);
        return new ApiResponse(user, "User created successfully", HttpStatus.OK);
    }

    public ApiResponse deleteAllUsers() {
        userRepository.deleteAll();
        return null;
    }

}
