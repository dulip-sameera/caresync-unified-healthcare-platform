package dev.dulipsameera.patientservice.paitent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.ArgumentMatchers.any;

import dev.dulipsameera.patientservice.dto.PatientDto;
import dev.dulipsameera.patientservice.entity.PatientEntity;
import dev.dulipsameera.patientservice.entity.PatientStatusEntity;
import dev.dulipsameera.patientservice.entity.embedded.PatientAddress;
import dev.dulipsameera.patientservice.enums.PatientStatusEnum;
import dev.dulipsameera.patientservice.exception.custom.PatientNotFoundException;
import dev.dulipsameera.patientservice.exception.custom.PatientStatusNotFound;
import dev.dulipsameera.patientservice.repository.PatientRepository;
import dev.dulipsameera.patientservice.repository.PatientStatusRepository;
import dev.dulipsameera.patientservice.service.impl.PatientServiceImpl;
import dev.dulipsameera.patientservice.utils.mapper.PatientMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientStatusRepository patientStatusRepository;

    @Mock
    private PatientMapper patientMapper;

    // This creates an instance of PatientServiceImpl and injects the mocks
    @InjectMocks
    private PatientServiceImpl patientService;

    private UUID patientId;
    private String shareId;
    private PatientEntity patientEntity;
    private PatientDto patientDto;
    private PatientStatusEntity activeStatus;
    private PatientStatusEntity deletedStatus;


    @BeforeEach
    void setUp() {
        patientId = UUID.randomUUID();
        shareId = "PAT-25LK0001";
        // 1. Initialize PatientAddress
        PatientAddress patientAddress = new PatientAddress(
                "123 Main St", null, "Colombo", "Colombo", "Western Province", "00100"
        );

        // 2. Initialize PatientStatusEntity
        activeStatus = new PatientStatusEntity(PatientStatusEnum.ACTIVE.getId(), PatientStatusEnum.ACTIVE.getName(), null);
        deletedStatus = new PatientStatusEntity(PatientStatusEnum.DELETED.getId(), PatientStatusEnum.DELETED.getName(), null);

        // 3. Initialize PatientEntity with all required fields
        patientEntity = PatientEntity.builder()
                .id(patientId)
                .shareId(shareId)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@email.com")
                .nic("901234567V")
                .contactNo("0712345678")
                .dateOfBirth(LocalDate.of(1990, 5, 15))
                .address(patientAddress) // Set the embedded object
                .userId(UUID.randomUUID())
                .createdAt(LocalDateTime.now())
                .statusId(activeStatus) // Set the related object
                .build();

        // This is a simple DTO to be returned by the mocked mapper
        patientDto = new PatientDto();
        patientDto.setShareId(shareId);
        patientDto.setFirstName("John");
    }

    /**
     * Test case for the happy path where a patient is found by their ID.
     * This test ensures the service method returns the correct DTO and
     * the repository and mapper are called as expected.
     */
    @Test
    void getPatientByIdShouldReturnPatientDtoWhenPatientIsFound() {
        // Arrange: Mock the behavior of the repository and mapper
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patientEntity));
        when(patientMapper.toDto(patientEntity)).thenReturn(patientDto);

        // Act: Call the method to be tested
        PatientDto result = patientService.getPatientById(patientId);

        // Assert: Verify the result and mock interactions
        assertEquals(patientDto, result);
        verify(patientRepository, times(1)).findById(patientId);
        verify(patientMapper, times(1)).toDto(patientEntity);
    }

    /**
     * Test case for the unhappy path where a patient is NOT found by their ID.
     * This test ensures that the correct exception is thrown.
     */
    @Test
    void getPatientByIdShouldThrowExceptionWhenNotFound() {
        // Arrange: Mock the repository to return an empty optional
        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        // Act & Assert: Use assertThrows to check for the exception
        assertThrows(PatientNotFoundException.class, () -> patientService.getPatientById(patientId));

        // Verify: Ensure the repository was called but the mapper was NOT called
        verify(patientRepository, times(1)).findById(patientId);
        verify(patientMapper, never()).toDto(patientEntity);
    }

    /**
     * Test case for the happy path where a patient is found by their shareId.
     * This test ensures the service method returns the correct DTO and
     * the repository and mapper are called as expected.
     */
    @Test
    void getPatientByShareIdShouldReturnPatientDtoWhenFound() {
        // Arrange: Mock the repository to return a patient and the mapper to return a DTO
        when(patientRepository.findByShareId(shareId)).thenReturn(Optional.of(patientEntity));
        when(patientMapper.toDto(patientEntity)).thenReturn(patientDto);

        // Act: Call the service method
        PatientDto result = patientService.getPatientByShareId(shareId);

        // Assert: Check if the returned DTO is correct and verify mock calls
        assertEquals(patientDto, result);
        verify(patientRepository, times(1)).findByShareId(shareId);
        verify(patientMapper, times(1)).toDto(patientEntity);
    }

    /**
     * Test case for the unhappy path where a patient is NOT found by their shareId.
     * This test ensures that the correct exception is thrown.
     */
    @Test
    void getPatientByShareIdShouldThrowExceptionWhenNotFound() {
        // Arrange: Mock the repository to return an empty optional
        when(patientRepository.findByShareId(shareId)).thenReturn(Optional.empty());

        // Act & Assert: Use assertThrows to check for the exception
        assertThrows(PatientNotFoundException.class, () -> patientService.getPatientByShareId(shareId));

        // Verify: Ensure the repository was called and the mapper was NOT called
        verify(patientRepository, times(1)).findByShareId(shareId);
        verify(patientMapper, never()).toDto(patientEntity);
    }

    // --- New tests for the deletePatientById method ---

    /**
     * Test case for the happy path where a patient is successfully marked as deleted.
     * This test verifies that the patient's status is updated to DELETED and the repository's save() method is called.
     */
    @Test
    void deletePatientByIdShouldMarkPatientAsDeletedWhenFound() {
        // Arrange: Mock repository calls to return the patient entity and the DELETED status entity
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patientEntity));
        when(patientStatusRepository.findById(PatientStatusEnum.DELETED.getId())).thenReturn(Optional.of(deletedStatus));

        // Use ArgumentCaptor to verify the exact object being saved
        ArgumentCaptor<PatientEntity> patientEntityCaptor = ArgumentCaptor.forClass(PatientEntity.class);

        // Act: Call the service method
        patientService.deletePatientById(patientId);

        // Assert & Verify:
        // 1. Verify that findById was called once
        verify(patientRepository, times(1)).findById(patientId);
        // 2. Verify that findById for status was called once
        verify(patientStatusRepository, times(1)).findById(PatientStatusEnum.DELETED.getId());
        // 3. Verify that save was called once with the captured entity
        verify(patientRepository, times(1)).save(patientEntityCaptor.capture());

        // 4. Assert that the captured entity's status is correctly updated to DELETED
        assertEquals(deletedStatus.getId(), patientEntityCaptor.getValue().getStatusId().getId());
    }

    /**
     * Test case for when the patient to be deleted is not found.
     * This test verifies that a PatientNotFoundException is thrown.
     */
    @Test
    void deletePatientByIdShouldThrowExceptionWhenPatientNotFound() {
        // Arrange: Mock findById to return an empty Optional
        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        // Act & Assert: Verify that the correct exception is thrown
        assertThrows(PatientNotFoundException.class, () -> patientService.deletePatientById(patientId));

        // Verify: Ensure the repository was called but no save operation was performed
        verify(patientRepository, times(1)).findById(patientId);
        verify(patientRepository, never()).save(any(PatientEntity.class));
    }

    /**
     * Test case for when the patient to be deleted is already marked as deleted.
     * This test verifies that a PatientNotFoundException is thrown.
     */
    @Test
    void deletePatientByIdShouldThrowExceptionWhenAlreadyDeleted() {
        // Arrange: Set the patient's status to DELETED and mock findById to return it
        patientEntity.setStatusId(deletedStatus);
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patientEntity));

        // Act & Assert: Verify that the correct exception is thrown
        assertThrows(PatientNotFoundException.class, () -> patientService.deletePatientById(patientId));

        // Verify: Ensure the repository's findById was called but no save was performed
        verify(patientRepository, times(1)).findById(patientId);
        verify(patientRepository, never()).save(any(PatientEntity.class));
    }

    /**
     * Test case for when the input ID is null.
     * This test verifies that an IllegalArgumentException is thrown.
     */
    @Test
    void deletePatientByIdShouldThrowIllegalArgumentExceptionForNullId() {
        // Arrange: Null ID
        UUID nullId = null;

        // Act & Assert: Verify that the correct exception is thrown
        assertThrows(IllegalArgumentException.class, () -> patientService.deletePatientById(nullId));

        // Verify: Ensure no repository methods were called
        verify(patientRepository, never()).findById(any());
        verify(patientRepository, never()).save(any(PatientEntity.class));
    }

    /**
     * Test case for when the DELETED status is not found in the repository.
     * This test verifies that a PatientStatusNotFound exception is thrown.
     */
    @Test
    void deletePatientByIdShouldThrowExceptionWhenStatusNotFound() {
        // Arrange: Mock findById to return the patient entity, but mock the status repository to return empty
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patientEntity));
        when(patientStatusRepository.findById(PatientStatusEnum.DELETED.getId())).thenReturn(Optional.empty());

        // Act & Assert: Verify that the correct exception is thrown
        assertThrows(PatientStatusNotFound.class, () -> patientService.deletePatientById(patientId));

        // Verify: Ensure both findById calls were made, but no save operation was performed
        verify(patientRepository, times(1)).findById(patientId);
        verify(patientStatusRepository, times(1)).findById(PatientStatusEnum.DELETED.getId());
        verify(patientRepository, never()).save(any(PatientEntity.class));
    }
}
