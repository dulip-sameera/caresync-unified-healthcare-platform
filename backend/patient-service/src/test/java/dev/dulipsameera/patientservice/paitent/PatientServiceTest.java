package dev.dulipsameera.patientservice.paitent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import dev.dulipsameera.patientservice.dto.PatientDto;
import dev.dulipsameera.patientservice.entity.PatientEntity;
import dev.dulipsameera.patientservice.entity.PatientStatusEntity;
import dev.dulipsameera.patientservice.entity.embedded.PatientAddress;
import dev.dulipsameera.patientservice.repository.PatientRepository;
import dev.dulipsameera.patientservice.service.impl.PatientServiceImpl;
import dev.dulipsameera.patientservice.utils.mapper.PatientMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientMapper patientMapper;

    @InjectMocks
    private PatientServiceImpl patientService;

    private UUID patientId;
    private PatientEntity patientEntity;
    private PatientDto patientDto;

    @BeforeEach
    void setUp() {
        patientId = UUID.randomUUID();
        // 1. Initialize PatientAddress
        PatientAddress patientAddress = new PatientAddress(
                "123 Main St", null, "Colombo", "Colombo", "Western Province", "00100"
        );

        // 2. Initialize PatientStatusEntity
        PatientStatusEntity patientStatus = new PatientStatusEntity(1, "Active", null);

        // 3. Initialize PatientEntity with all required fields
        patientEntity = PatientEntity.builder()
                .id(patientId)
                .shareId("PAT-25LK0001")
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@email.com")
                .nic("901234567V")
                .contactNo("0712345678")
                .dateOfBirth(LocalDate.of(1990, 5, 15))
                .address(patientAddress)
                .userId(UUID.randomUUID())
                .createdAt(LocalDateTime.now())
                .statusId(patientStatus)
                .build();
        patientDto = new PatientDto();
    }

    @Test
    void getPatientByIdShouldReturnPatientDtoWhenPatientIsFound() {
        // Arrange
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patientEntity));
        when(patientMapper.toDto(patientEntity)).thenReturn(patientDto);

        // Act
        PatientDto result = patientService.getPatientById(patientId);

        // Assert
        assertEquals(patientDto, result);
        verify(patientRepository, times(1)).findById(patientId);
        verify(patientMapper, times(1)).toDto(patientEntity);
    }
}
