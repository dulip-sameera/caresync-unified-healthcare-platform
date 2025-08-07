package dev.dulipsameera.patientservice.entity;

import dev.dulipsameera.patientservice.entity.embeddable.Address;
import dev.dulipsameera.patientservice.enums.PatientStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "patient")
public class PatientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String shareId;
    private String firstName;
    private String lastName;
    private String email;
    private String nic;
    private String contactNo;
    private LocalDate dateOfBirth;
    @Embedded
    private Address address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
