package dev.dulipsameera.patientservice.repository;

import dev.dulipsameera.patientservice.entity.PatientStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientStatusRepository extends JpaRepository<PatientStatusEntity, Integer> {
    Optional<PatientStatusEntity> findByName(String name);
}
