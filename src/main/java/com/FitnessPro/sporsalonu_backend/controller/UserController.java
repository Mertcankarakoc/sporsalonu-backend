package com.FitnessPro.sporsalonu_backend.controller;

import com.FitnessPro.sporsalonu_backend.dto.UserCreateRequest;
import com.FitnessPro.sporsalonu_backend.dto.UserUpdateRequest;
import com.FitnessPro.sporsalonu_backend.helpers.CustomUserDetails;
import com.FitnessPro.sporsalonu_backend.model.ApiResponse;
import com.FitnessPro.sporsalonu_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/deleteByUser/{id}")
    public ApiResponse deleteUser(@PathVariable UUID id) {
        return userService.deleteUser(id);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/getAllUsers")
    public ApiResponse getAllUsers() {
        return userService.getAllUsers();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/getUserById/{id}")
    public ApiResponse getUserById(@PathVariable UUID id) {
        return userService.getUserById(id);
    }

    @PutMapping("/updateProfile")
    public ApiResponse updateProfile(@RequestBody UserUpdateRequest userUpdateRequest, @RequestHeader("Authorization") String authHeader) {
        return userService.updateProfile(userUpdateRequest, authHeader);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/createUser")
    public ApiResponse createUser(@RequestBody UserCreateRequest userCreateRequest) {
        return userService.createUser(userCreateRequest);
    }

}
