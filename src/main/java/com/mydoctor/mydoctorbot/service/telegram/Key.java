package com.mydoctor.mydoctorbot.service.telegram;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static com.mydoctor.mydoctorbot.service.telegram.Step.REGISTER_DOCTOR_ASK_FIRST_NAME;

@Getter
@RequiredArgsConstructor
public enum Key {
    REGISTER_DOCTOR("I'm a doctor", REGISTER_DOCTOR_ASK_FIRST_NAME),
    FIND_DOCTOR("I'm a patient", null);

    private final String message;
    private final Step firstStep;

    public static Optional<Key> findFlowKey(String text) {
        return Arrays.stream(values())
                .filter(Key::isFlow)
                .filter(v -> v.message.equals(text))
                .findFirst();
    }

    public boolean isFlow() {
        return Objects.nonNull(firstStep);
    }
}
