package com.mydoctor.mydoctorbot.service;

import com.google.common.collect.Lists;
import com.mydoctor.mydoctorbot.model.Doctor;
import com.mydoctor.mydoctorbot.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public Doctor create(String firstName, String lastName, String specialization) {
        var doctor = new Doctor();
        doctor.setFirstName(firstName);
        doctor.setLastName(lastName);
        doctor.setSpecialization(specialization);

        Doctor saved = doctorRepository.save(doctor);
        return saved;
    }

    public List<Doctor> getAll() {
        return Lists.newArrayList(doctorRepository.findAll());
    }
}
