package com.mydoctor.mydoctorbot.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
@ToString
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer yearStartExperience;
    private String specialization;
    private Point<G2D> location;

    @OneToOne
    @JoinColumn(name = "system_user_id")
    private SystemUser systemUser;
}
