package com.FitnessPro.sporsalonu_backend.model;

public enum RoleType {
    ROLE_ADMIN("ROLE_ADMIN", "Admin"),
    ROLE_USER("ROLE_USER", "User"),
    ROLE_TRAINER("ROLE_TRAINER", "Trainer");

    private final String name;
    private final String description;

    RoleType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
