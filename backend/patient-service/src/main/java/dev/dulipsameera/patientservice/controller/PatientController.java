package dev.dulipsameera.patientservice.controller;

import dev.dulipsameera.patientservice.dto.PatientDto;
import dev.dulipsameera.patientservice.service.PatientService;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/patients")
public class PatientController {

    PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<Slice<PatientDto>> getAllPatients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        return new ResponseEntity<>(patientService.getAllPatients(page, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> getPatientById(@PathVariable UUID id) {
        return new ResponseEntity<>(patientService.getPatientById(id), HttpStatus.OK);
    }

    @GetMapping("/shareId/{shareId}")
    public ResponseEntity<PatientDto> getPatientByShareId(@PathVariable String shareId) {
        return new ResponseEntity<>(patientService.getPatientByShareId(shareId), HttpStatus.OK);
    }
}
