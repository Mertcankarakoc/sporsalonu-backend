package com.FitnessPro.sporsalonu_backend.repository;

import com.FitnessPro.sporsalonu_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
        Optional<User> findByEmail(String email);
}
