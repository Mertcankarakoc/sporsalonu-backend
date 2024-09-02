package com.FitnessPro.sporsalonu_backend.repository;

import com.FitnessPro.sporsalonu_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
        User findByEmail(String email);

        boolean existsByEmail(String email);
}