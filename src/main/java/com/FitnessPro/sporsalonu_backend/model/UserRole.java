package com.FitnessPro.sporsalonu_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleType name;

    @Transient
    private String desc;

    public UserRole(RoleType name) {
        this.name = name;
    }
    public String getDesc() {
        return name.getDescription();
    }
}
}