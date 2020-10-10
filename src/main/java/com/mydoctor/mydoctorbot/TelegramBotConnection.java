package com.mydoctor.mydoctorbot;

import com.mydoctor.mydoctorbot.model.Patient;
import com.mydoctor.mydoctorbot.service.DoctorService;
import com.mydoctor.mydoctorbot.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
public class TelegramBotConnection extends TelegramLongPollingBot {

    private final String token;

    @Autowired
    private DoctorService doctorService;
    @Autowired
    private PatientService patientService;

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();

            if ("I'm a patient".equals(text)) {
                execute(new SendMessage()
                        .setChatId(update.getMessage().getChatId())
                        .setText("You are a patient"));
                return;
            }

            String[] split = text.split(" ");
//            Doctor doctor = doctorService.create(split[0], split[1], split[2]);
            Patient patient = patientService.create(split[0], split[1]);
            SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                    .setChatId(update.getMessage().getChatId())
                    .setReplyMarkup(twoKeys())
//                    .setText("Doctor created: " + doctor);
                    .setText("Patient created: " + patient);
            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private ReplyKeyboard twoKeys() {
        var rkm = new ReplyKeyboardMarkup();
        KeyboardRow keyboardButtons = new KeyboardRow();
        keyboardButtons.add("I'm a doctor");
        keyboardButtons.add("I'm a patient");
        rkm.setKeyboard(Collections.singletonList(keyboardButtons));
        rkm.setResizeKeyboard(true);

        return rkm;
    }


    @Override
    public String getBotUsername() {
        return "My Doctor";
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @SneakyThrows
//    @Scheduled(fixedDelay = 5000)
    public void sendIt() {
        execute(new SendMessage()
                .setChatId(241931659L)
                .setText("Happy birth day!"));
    }
}
