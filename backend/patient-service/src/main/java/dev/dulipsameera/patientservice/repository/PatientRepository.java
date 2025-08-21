package dev.dulipsameera.patientservice.repository;

import dev.dulipsameera.patientservice.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface PatientRepository extends JpaRepository<PatientEntity, UUID> {
    Optional<PatientEntity> findByEmail(String email);
    Optional<PatientEntity> findByShareId(String shareId);
    Optional<PatientEntity> findByNic(String nic);
    Optional<PatientEntity> findByContactNo(String contactNo);
    Optional<PatientEntity> findByUserId(UUID userId);

    @Query("SELECT MAX(p.shareId) FROM PatientEntity p WHERE p.shareId LIKE :prefix%")
    String findMaxShareIdStartingWith(@Param("prefix") String prefix);
}
