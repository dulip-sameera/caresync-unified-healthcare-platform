package dev.dulipsameera.patientservice.repository;

import dev.dulipsameera.patientservice.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PatientRepository extends JpaRepository<PatientEntity, UUID> {
    Optional<PatientEntity> findByEmail(String email);
    Optional<PatientEntity> findByShareId(String shareId);
    Optional<PatientEntity> findByNic(String nic);
    Optional<PatientEntity> findByContactNo(String contactNo);
    Optional<PatientEntity> findByUserId(UUID userId);
}
