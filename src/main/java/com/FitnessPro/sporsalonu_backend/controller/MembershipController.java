package com.FitnessPro.sporsalonu_backend.controller;

import com.FitnessPro.sporsalonu_backend.dto.CreateMembershipDto;
import com.FitnessPro.sporsalonu_backend.model.ApiResponse;
import com.FitnessPro.sporsalonu_backend.repository.MembershipRepository;
import com.FitnessPro.sporsalonu_backend.service.MembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/membership")
@RequiredArgsConstructor
public class MembershipController {

    private final MembershipService membershipService;

    @PostMapping("/createMembership")
    public ApiResponse createMembership(@RequestParam UUID userId, @RequestBody CreateMembershipDto createMembershipDto) {
        return membershipService.createMembership(userId, createMembershipDto);
    }

    @GetMapping("/all")
    public ApiResponse getMembershipAll() {
        return membershipService.getMembershipAll();
    }

    @GetMapping("/membershipGetById/{id}")
    public ApiResponse getMembershipById(@PathVariable UUID id) {
        return membershipService.getMembershipById(id);
    }

    @DeleteMapping("/deleteMembership/{id}")
    public ApiResponse deleteMembership(@PathVariable UUID id) {
        return membershipService.deleteMembership(id);
    }

}
