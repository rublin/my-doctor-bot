package com.mydoctor.mydoctorbot;

import com.mydoctor.mydoctorbot.model.Doctor;
import com.mydoctor.mydoctorbot.service.DoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@RequiredArgsConstructor
public class TelegramBotConnection extends TelegramLongPollingBot {

    private final String token;

    @Autowired
    private DoctorService doctorService;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            String[] split = text.split(" ");
            Doctor doctor = doctorService.create(split[0], split[1], split[2]);
            SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                    .setChatId(update.getMessage().getChatId())
                    .setText("Doctor created: " + doctor);
            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "My Doctor";
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
