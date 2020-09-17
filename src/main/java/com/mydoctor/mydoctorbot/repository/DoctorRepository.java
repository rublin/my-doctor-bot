package com.mydoctor.mydoctorbot.repository;

import com.mydoctor.mydoctorbot.model.Doctor;
import org.springframework.data.repository.CrudRepository;

public interface DoctorRepository extends CrudRepository<Doctor, Long> {
}
