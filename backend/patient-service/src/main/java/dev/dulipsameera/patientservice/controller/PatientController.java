package dev.dulipsameera.patientservice.controller;

import dev.dulipsameera.patientservice.dto.PatientDto;
import dev.dulipsameera.patientservice.entity.PatientEntity;
import dev.dulipsameera.patientservice.service.PatientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public List<PatientDto> getAllPatients() {
      return patientService.getAllPatients();
    }

    @PostMapping
    public PatientDto savePatient(@RequestBody PatientDto patientDto) {
      return patientService.savePatient(patientDto);
    }

}
