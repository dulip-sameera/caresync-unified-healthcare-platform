package dev.dulipsameera.patientservice.entity;

import dev.dulipsameera.patientservice.enums.PatientStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "patient_status")
public class PatientStatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(50)", name = "name", nullable = false)
    private PatientStatusEnum name;

}
