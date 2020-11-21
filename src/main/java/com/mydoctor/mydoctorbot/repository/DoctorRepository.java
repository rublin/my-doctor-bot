package com.mydoctor.mydoctorbot.repository;

import com.mydoctor.mydoctorbot.model.Doctor;
import org.geolatte.geom.Point;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DoctorRepository extends CrudRepository<Doctor, Long> {

    @Query(value = "select * from doctor WHERE ST_DWithin(location, :from, :distanceInMeter)", nativeQuery = true)
    List<Doctor> findByLocation(Point from, Long distanceInMeter);
}
