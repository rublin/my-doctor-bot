package com.mydoctor.mydoctorbot.repository;

import com.mydoctor.mydoctorbot.model.Patient;
import org.springframework.data.repository.CrudRepository;

public interface PatientRepository extends CrudRepository<Patient, Long> {

}
