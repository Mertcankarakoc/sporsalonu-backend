package com.FitnessPro.sporsalonu_backend.repository;

import com.FitnessPro.sporsalonu_backend.model.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MembershipRepository extends JpaRepository<Membership, UUID> {
    List<Membership> findByStatus(String status);

    List<Membership> findByUserId(UUID userId);
}
