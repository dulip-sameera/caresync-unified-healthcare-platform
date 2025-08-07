package dev.dulipsameera.patientservice.repository;

import dev.dulipsameera.patientservice.enums.PatientStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientStatusRepository extends JpaRepository<PatientStatusEntity, UUID> {
    Optional<PatientStatusEntity> findByName(PatientStatusEnum name);
}
