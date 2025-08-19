package dev.dulipsameera.patientservice.entity;

import dev.dulipsameera.patientservice.entity.embedded.PatientAddress;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "patient")
public class PatientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotNull
    @Column(unique = true)
    private String shareId;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    @Column(unique = true)
    private String email;
    @NotNull
    @Column(unique = true)
    private String nic;
    @NotNull
    @Column(unique = true, length = 10)
    private String contactNo;
    @NotNull
    private LocalDate dateOfBirth;
    @Embedded
    private PatientAddress address;
    @NotNull
    @Column(unique = true)
    private UUID userId;
    @NotNull
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private PatientStatusEntity statusId;
}


