package com.FitnessPro.sporsalonu_backend.controller;

import com.FitnessPro.sporsalonu_backend.dto.UserCreateRequest;
import com.FitnessPro.sporsalonu_backend.dto.UserUpdateRequest;
import com.FitnessPro.sporsalonu_backend.model.ApiResponse;
import com.FitnessPro.sporsalonu_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @DeleteMapping("/deleteByUser/{id}")
    public ApiResponse deleteUser(@PathVariable UUID id) {
        return userService.deleteUser(id);
    }

    @GetMapping("/getAllUsers")
    public ApiResponse getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/getUserById/{id}")
    public ApiResponse getUserById(@PathVariable UUID id) {
        return userService.getUserById(id);
    }

    @PutMapping("/updateUser")
    public ApiResponse updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
        return userService.updateUser(userUpdateRequest);
    }

    @GetMapping("/getUserByEmail/{email}")
    public ApiResponse getUsersByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @PostMapping("/createUser")
    public ApiResponse createUser(@RequestBody UserCreateRequest userCreateRequest) {
        return userService.createUser(userCreateRequest);
    }

}
