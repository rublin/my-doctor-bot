package com.mydoctor.mydoctorbot.model;

import com.mydoctor.mydoctorbot.service.telegram.Step;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Data
@Entity
@Accessors(chain = true)
public class SystemUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long telegramId;

    @Enumerated(EnumType.STRING)
    private Step step;

    private String firstName;
    private String lastName;

    public boolean hasFlow() {
        return Objects.nonNull(step);
    }
}
