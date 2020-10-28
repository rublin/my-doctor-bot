package com.mydoctor.mydoctorbot.service.telegram;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Step {
    REGISTER_DOCTOR_ASK_LOCATION(null),
    REGISTER_DOCTOR_ASK_SPECIALTY(REGISTER_DOCTOR_ASK_LOCATION),
    REGISTER_DOCTOR_ASK_LAST_NAME(REGISTER_DOCTOR_ASK_SPECIALTY),
    REGISTER_DOCTOR_FIRST_NAME(REGISTER_DOCTOR_ASK_LAST_NAME);

    //    private final Key flow;
    private final Step nextStep;

    public boolean isLast() {
        return nextStep == null;
    }
}
