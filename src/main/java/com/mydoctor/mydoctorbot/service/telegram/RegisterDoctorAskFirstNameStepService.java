package com.mydoctor.mydoctorbot.service.telegram;

import com.mydoctor.mydoctorbot.model.SystemUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import static java.lang.String.format;

@Component
@RequiredArgsConstructor
public class RegisterDoctorAskFirstNameStepService implements StepService {
    private final SystemUserService systemUserService;

    @Override
    public Step getStep() {
        return Step.REGISTER_DOCTOR_ASK_FIRST_NAME;
    }

    @Override
    public SendMessage processStep(Message message, SystemUser systemUser) {
        systemUser.setStep(getStep().getNextStep());
        systemUser.setFirstName(message.getText());
        systemUserService.update(systemUser);

        return new SendMessage()
                .setChatId(systemUser.getTelegramId())
                .setText(format("Ok, I saved %s as you first name.\n\n", message.getText()) +
                        "What is your last name?");
    }
}
