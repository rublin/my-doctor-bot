package com.mydoctor.mydoctorbot.service.telegram;

import com.mydoctor.mydoctorbot.model.SystemUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.mydoctor.mydoctorbot.service.telegram.Step.REGISTER_DOCTOR_ASK_LAST_NAME;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class RegisterDoctorAskLastNameStepService implements StepService {
    private final SystemUserService systemUserService;

    @Override
    public Step getStep() {
        return REGISTER_DOCTOR_ASK_LAST_NAME;
    }

    @Override
    public SendMessage processStep(Message message, SystemUser systemUser) {
        systemUser.setStep(getStep().getNextStep());
        systemUser.setLastName(message.getText());
        systemUserService.update(systemUser);
        return new SendMessage()
                .setChatId(systemUser.getTelegramId())
                .setText(format("Ok, I saved %s as you last name.\n\n", message.getText()) +
                        "What is your specialty?");
    }

}
