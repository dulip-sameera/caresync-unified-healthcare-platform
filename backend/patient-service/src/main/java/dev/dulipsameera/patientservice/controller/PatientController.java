package dev.dulipsameera.patientservice.controller;

import dev.dulipsameera.patientservice.entity.PatientEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @GetMapping
    public List<PatientEntity> getAllPatients() {
      return null;
    }

    @PostMapping
    public PatientEntity savePatient(@RequestBody PatientEntity patientEntity) {
      return null;
    }

}
