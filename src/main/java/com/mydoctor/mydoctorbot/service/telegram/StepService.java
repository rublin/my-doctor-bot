package com.mydoctor.mydoctorbot.service.telegram;

import com.mydoctor.mydoctorbot.model.SystemUser;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface StepService {
    Step getStep();

    SendMessage processStep(Message message, SystemUser systemUser);
}
