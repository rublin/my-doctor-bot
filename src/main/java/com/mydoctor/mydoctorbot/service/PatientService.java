package com.mydoctor.mydoctorbot.service;

import com.google.common.collect.Lists;
import com.mydoctor.mydoctorbot.model.Patient;
import com.mydoctor.mydoctorbot.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Patient create(String firstName, String lastName) {
        Patient patient = new Patient();
        patient.setFirstName(firstName);
        patient.setLastName(lastName);

        Patient saved = patientRepository.save(patient);
        return saved;
    }

    public List<Patient> getAll() {
        return Lists.newArrayList(patientRepository.findAll());
    }
}
