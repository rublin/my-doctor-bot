package com.mydoctor.mydoctorbot.service.telegram;

import com.mydoctor.mydoctorbot.model.Doctor;
import com.mydoctor.mydoctorbot.model.SystemUser;
import com.mydoctor.mydoctorbot.service.DoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalDate;

import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterDoctorAskExperience implements StepService {

    private final DoctorService doctorService;
    private final SystemUserService systemUserService;

    @Override
    public Step getStep() {
        return Step.REGISTER_DOCTOR_ASK_EXPERIENCE;
    }

    @Override
    public SendMessage processStep(Message message, SystemUser systemUser) {
        SendMessage sendMessage = new SendMessage()
                .setChatId(systemUser.getTelegramId());

        try {
            int totalExperience = Integer.parseInt(message.getText());
            systemUser.setStep(getStep().getNextStep());

            Doctor doctor = systemUser.getDoctor();
            doctor.setYearStartExperience(LocalDate.now().getYear() - totalExperience);

            systemUserService.update(systemUser);
            doctorService.save(doctor);

            return sendMessage.setText(format("Ok, I saved %d as year of your start of experience.\n\n", doctor.getYearStartExperience()) +
                    "And the last question. What is your location?");
        } catch (NumberFormatException e) {
            log.error("Failed to parse [{}] as int", message.getText());
            return sendMessage.setText("Wrong format. Only digits accepted. Please, try again");
        }
    }
}
