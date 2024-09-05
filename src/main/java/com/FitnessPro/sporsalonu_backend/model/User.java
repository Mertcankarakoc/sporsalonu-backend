package com.FitnessPro.sporsalonu_backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class User{

    @Id
    @GeneratedValue
    private UUID id;

    private UUID packageId;
    private String firstName;
    private String lastName;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private String phoneNumber;
    private Address address;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime birthDay;

    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;
    private boolean isActive;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<UserRole> roles;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Membership> membership;
}