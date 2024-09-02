package com.FitnessPro.sporsalonu_backend.configurations;

import com.FitnessPro.sporsalonu_backend.model.RoleType;
import com.FitnessPro.sporsalonu_backend.model.UserRole;
import com.FitnessPro.sporsalonu_backend.repository.UserRepository;
import com.FitnessPro.sporsalonu_backend.repository.UserRoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class InıtialConfig {

        @Autowired
        UserRoleRepository roleRepo;
        @Autowired
        UserRepository userRepo;

        @PostConstruct
        private void initAuthoritiesAndAdminUser() {
            long roleCount = roleRepo.count();

            if (roleCount == 0) {
                Set<UserRole> roleSet = new HashSet<>();
                roleSet.add(new UserRole(RoleType.ROLE_ADMIN));
                roleSet.add(new UserRole(RoleType.ROLE_USER));
                roleSet.add(new UserRole(RoleType.ROLE_TRAINER));

                roleRepo.saveAll(roleSet);
            }

    }
}
