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

    @GetMapping("/contact/{contact}")
    public ResponseEntity<PatientDto> getPatientByContact(@PathVariable String contact){
        return new ResponseEntity<>(patientService.getPatientByContact(contact), HttpStatus.OK);
    }

    @GetMapping("/nic/{nic}")
    public ResponseEntity<PatientDto> getPatientByNic(@PathVariable String nic){
        return new ResponseEntity<>(patientService.getPatientByNic(nic), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<PatientDto> getPatientByEmail(@PathVariable String email){
        return new ResponseEntity<>(patientService.getPatientByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<PatientDto> getPatientByUserId(@PathVariable UUID userId){
        return new ResponseEntity<>(patientService.getPatientByUserId(userId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatientById(@PathVariable UUID id) {
        patientService.deletePatientById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<PatientDto> createPatient(@RequestBody PatientDto patientDto) {
        return new ResponseEntity<>(patientService.createPatient(patientDto), HttpStatus.CREATED);
    }
}
