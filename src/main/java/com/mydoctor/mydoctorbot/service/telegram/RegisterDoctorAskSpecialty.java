package com.mydoctor.mydoctorbot.service.telegram;

import com.mydoctor.mydoctorbot.model.Doctor;
import com.mydoctor.mydoctorbot.model.SystemUser;
import com.mydoctor.mydoctorbot.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.mydoctor.mydoctorbot.service.telegram.Step.REGISTER_DOCTOR_ASK_SPECIALTY;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class RegisterDoctorAskSpecialty implements StepService {
    private final SystemUserService systemUserService;
    private final DoctorService doctorService;

    @Override
    public Step getStep() {
        return REGISTER_DOCTOR_ASK_SPECIALTY;
    }

    @Override
    public SendMessage processStep(Message message, SystemUser systemUser) {
        systemUser.setStep(getStep().getNextStep());

        Doctor doctor = new Doctor();
        doctor.setSpecialization(message.getText());
        doctor.setSystemUser(systemUser);

        systemUserService.update(systemUser);
        doctorService.save(doctor);

        return new SendMessage()
                .setChatId(systemUser.getTelegramId())
                .setText(format("Ok, I saved %s as your specialty.\n\n", message.getText()) +
                        "How many years of experience do you have?");
    }

}
