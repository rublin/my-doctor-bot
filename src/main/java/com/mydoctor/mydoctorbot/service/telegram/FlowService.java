package com.mydoctor.mydoctorbot.service.telegram;

import com.mydoctor.mydoctorbot.model.SystemUser;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface FlowService {

    Key getFlow();

    SendMessage startFlow(SystemUser systemUser);
}
