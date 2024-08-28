package com.FitnessPro.sporsalonu_backend.service;

import com.FitnessPro.sporsalonu_backend.dto.CreateMembershipDto;
import com.FitnessPro.sporsalonu_backend.model.ApiResponse;
import com.FitnessPro.sporsalonu_backend.model.Membership;
import com.FitnessPro.sporsalonu_backend.model.User;
import com.FitnessPro.sporsalonu_backend.repository.MembershipRepository;
import com.FitnessPro.sporsalonu_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MembershipService {

    private final MembershipRepository membershipRepository;
    private final UserRepository userRepository;

    public ApiResponse getMembershipAll(){
        try {
            return new ApiResponse(membershipRepository.findAll(), "Memberships fetched successfully", HttpStatus.OK);
        }catch (Exception e){
            log.error("Error while fetching memberships", e);
            return new ApiResponse(null, "Error while fetching memberships", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ApiResponse getMembershipById(UUID id){
        try {
            var membership = membershipRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Membership not found"));
            return new ApiResponse(membership, "Membership fetched successfully", HttpStatus.OK);
        }catch (Exception e){
            log.error("Error while fetching membership", e);
            return new ApiResponse(null, "Error while fetching membership", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ApiResponse createMembership(UUID userId, CreateMembershipDto createMembershipDto) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            Membership membership = Membership.builder()
                    .startDate(createMembershipDto.getStartDate())
                    .totalDays(createMembershipDto.getTotalDays())
                    .remainingDays(createMembershipDto.getTotalDays())
                    .status(createMembershipDto.getStatus())
                    .isActive(createMembershipDto.isActive())
                    .user(user)
                    .build();

            membershipRepository.save(membership);
            return new ApiResponse(null, "Membership created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ApiResponse deleteMembership(UUID id) {
        try {
            membershipRepository.deleteById(id);
            return new ApiResponse(null, "Membership deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while deleting membership", e);
            return new ApiResponse(null, "Error while deleting membership", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<Membership> getExpiredMemberships() {
        return membershipRepository.findByStatus("EXPIRED");
    }

    public List<Membership> getActiveMemberships() {
        return membershipRepository.findByStatus("ACTIVE");
    }
}