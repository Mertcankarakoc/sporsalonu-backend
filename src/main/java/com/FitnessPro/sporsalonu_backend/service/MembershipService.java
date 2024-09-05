package com.FitnessPro.sporsalonu_backend.service;

import com.FitnessPro.sporsalonu_backend.dto.CreateMembershipDto;
import com.FitnessPro.sporsalonu_backend.dto.MembershipProfileDto;
import com.FitnessPro.sporsalonu_backend.dto.UpdateMembershipDto;
import com.FitnessPro.sporsalonu_backend.exceptions.ResourceNotFoundException;
import com.FitnessPro.sporsalonu_backend.model.ApiResponse;
import com.FitnessPro.sporsalonu_backend.model.Membership;
import com.FitnessPro.sporsalonu_backend.model.User;
import com.FitnessPro.sporsalonu_backend.repository.MembershipRepository;
import com.FitnessPro.sporsalonu_backend.repository.UserRepository;
import com.FitnessPro.sporsalonu_backend.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MembershipService {

    private final MembershipRepository membershipRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

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

    public ApiResponse updateMembership(UpdateMembershipDto updateMembershipDto, UUID membershipId, UUID userId) {
        // Üyeliği bul
        Membership membership = membershipRepository.findById(membershipId)
                .orElseThrow(() -> new ResourceNotFoundException("Membership not found"));

        // Kullanıcının güncelleme yetkisini kontrol et
        if (!membership.getUser().getId().equals(userId)) {
            return new ApiResponse(null, "Unauthorized to update this membership", HttpStatus.FORBIDDEN);
        }

        // Üyeliği güncelle
        membership.setStartDate(updateMembershipDto.getStartDate());
        membership.setTotalDays(updateMembershipDto.getTotalDays());
        membership.setRemainingDays(updateMembershipDto.getTotalDays());
        membership.setStatus(updateMembershipDto.getStatus());

        // Güncellenmiş üyeliği kaydet
        membershipRepository.save(membership);

        return new ApiResponse(membership, "Membership updated successfully", HttpStatus.OK);
    }

    public ApiResponse getUserMemberships(String authHeader) {
        // Token'dan kullanıcı ID'sini çıkar
        String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
        String userIdStr = jwtService.extractUserId(token);
        UUID userId = UUID.fromString(userIdStr);

        // Kullanıcının üyeliklerini bul
        List<Membership> memberships = membershipRepository.findByUserId(userId);

        // DTO'ya dönüştür
        List<MembershipProfileDto> membershipDTOs = memberships.stream().map(membership -> {
            MembershipProfileDto dto = new MembershipProfileDto();
            dto.setId(membership.getId());
            dto.setMembershipType(membership.getMembershipType());
            dto.setStartDate(membership.getStartDate());
            dto.setStatus(membership.getStatus());
            dto.setRemainingDays(membership.getRemainingDays());
            dto.setTotalDays(membership.getTotalDays());
            return dto;
        }).collect(Collectors.toList());

        return new ApiResponse(membershipDTOs, "Üyelik bilgileri başarıyla alındı", HttpStatus.OK);
    }
}