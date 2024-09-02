package com.FitnessPro.sporsalonu_backend.repository;

import com.FitnessPro.sporsalonu_backend.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByName(String name);
}