package com.mydoctor.mydoctorbot.service.telegram;

import com.mydoctor.mydoctorbot.model.SystemUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterDoctorFlowService implements FlowService {
    private final SystemUserService systemUserService;

    @Override
    public Key getFlow() {
        return Key.REGISTER_DOCTOR;
    }

    @Override
    public SendMessage startFlow(SystemUser systemUser) {
        systemUser.setStep(getFlow().getFirstStep());
        systemUserService.update(systemUser);

        return new SendMessage()
                .setChatId(systemUser.getTelegramId())
                .setText("You are going to register as a doctor. I need some information from you.\n" +
                        "This information will be available for your patients.\n\n" +
                        "What is your first name?");
    }
}
