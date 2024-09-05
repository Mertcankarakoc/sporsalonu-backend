package com.FitnessPro.sporsalonu_backend.controller;

import com.FitnessPro.sporsalonu_backend.dto.CreateMembershipDto;
import com.FitnessPro.sporsalonu_backend.dto.UpdateMembershipDto;
import com.FitnessPro.sporsalonu_backend.helpers.CustomUserDetails;
import com.FitnessPro.sporsalonu_backend.model.ApiResponse;
import com.FitnessPro.sporsalonu_backend.model.Membership;
import com.FitnessPro.sporsalonu_backend.service.MembershipService;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/membership")
@RequiredArgsConstructor
public class MembershipController {

    private final MembershipService membershipService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/createMembership")
    public ApiResponse createMembership(@RequestParam UUID userId, @RequestBody CreateMembershipDto createMembershipDto) {
        return membershipService.createMembership(userId, createMembershipDto);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/all")
    public ApiResponse getMembershipAll() {
        return membershipService.getMembershipAll();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/membershipGetById/{id}")
    public ApiResponse getMembershipById(@PathVariable UUID id) {
        return membershipService.getMembershipById(id);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/deleteMembership/{id}")
    public ApiResponse deleteMembership(@PathVariable UUID id) {
        return membershipService.deleteMembership(id);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/expired")
    public List<Membership> getExpiredMemberships() {
        return membershipService.getExpiredMemberships();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/active")
    public List<Membership> getActiveMemberships() {
        return membershipService.getActiveMemberships();
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/user")
    public ApiResponse getMyMembership(@AuthenticationPrincipal CustomUserDetails userDetails) {
        UUID userId = userDetails.getId();
        return membershipService.getMembershipByUserId(userId);
    }

    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    @PutMapping("/updateMembership/{id}")
    public ResponseEntity<ApiResponse> updateMembership(
            @PathVariable UUID id,
            @RequestBody UpdateMembershipDto updateMembershipDto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        UUID userId = customUserDetails.getId();
        ApiResponse response = membershipService.updateMembership(updateMembershipDto, id, userId);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}