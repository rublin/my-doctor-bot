package com.mydoctor.mydoctorbot.service;

import com.mydoctor.mydoctorbot.model.Doctor;
import com.mydoctor.mydoctorbot.model.SystemUser;
import com.mydoctor.mydoctorbot.repository.DoctorRepository;
import com.mydoctor.mydoctorbot.repository.SystemUserRepository;
import com.mydoctor.mydoctorbot.service.telegram.SystemUserService;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Geometries;
import org.geolatte.geom.Point;
import org.geolatte.geom.crs.CoordinateReferenceSystems;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DoctorServiceTest {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private SystemUserService systemUserService;

    @Autowired
    private SystemUserRepository systemUserRepository;

    private SystemUser systemUser;

    @BeforeAll
    public void init() {
        systemUser = systemUserService.auth(123L);
    }

    @AfterAll
    public void clean() {
        systemUserRepository.delete(systemUser);
    }

    @Test
    void save() {
        double lon = 50.365305;
        double lat = 31.327260;
        Doctor doctor = new Doctor();
        doctor.setSystemUser(systemUser);
        doctor.setSpecialization("test spec");
        doctor.setLocation(Geometries.mkPoint(new G2D(lon, lat), CoordinateReferenceSystems.WGS84));

        Doctor saved = doctorRepository.save(doctor);

        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals(lat, saved.getLocation().getPosition().getLat());
        assertEquals(lon, saved.getLocation().getPosition().getLon());

//        50.374536, 31.325564
        double patientLon = 50.374536;
        double patientLat = 31.325564;

        Point<G2D> patientLocation = Geometries.mkPoint(new G2D(patientLon, patientLat),
                CoordinateReferenceSystems.WGS84);

        assertFalse(doctorRepository.findByLocation(patientLocation, 1000L).isEmpty());
        assertTrue(doctorRepository.findByLocation(patientLocation, 500L).isEmpty());
    }
}