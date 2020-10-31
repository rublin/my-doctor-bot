package com.mydoctor.mydoctorbot;

import com.google.common.base.Functions;
import com.mydoctor.mydoctorbot.model.SystemUser;
import com.mydoctor.mydoctorbot.service.telegram.FlowService;
import com.mydoctor.mydoctorbot.service.telegram.Key;
import com.mydoctor.mydoctorbot.service.telegram.Step;
import com.mydoctor.mydoctorbot.service.telegram.StepService;
import com.mydoctor.mydoctorbot.service.telegram.SystemUserService;
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

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Slf4j
@RequiredArgsConstructor
public class TelegramBotConnection extends TelegramLongPollingBot {

    private final String token;

    @Autowired
    private SystemUserService systemUserService;

    @Autowired
    private List<StepService> stepServiceList;

    @Autowired
    private List<FlowService> flowServiceList;

    private Map<Step, StepService> stepServices;
    private Map<Key, FlowService> flowServices;

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        Long telegramId = update.getMessage().getChatId();
        SystemUser systemUser = systemUserService.auth(telegramId);

        if (systemUser.hasFlow()) {
            StepService stepService = stepServices.get(systemUser.getStep());
            SendMessage sendMessage = stepService.processStep(update.getMessage(), systemUser);
            execute(sendMessage);
            return;
        }

        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();

            Optional<Key> optional = Key.findFlowKey(text);
            if (optional.isPresent()) {
                Key key = optional.get();
                FlowService flowService = flowServices.get(key);
                SendMessage sendMessage = flowService.startFlow(systemUser);

                execute(sendMessage);
                return;
            }

            try {
                execute(new SendMessage()
                        .setChatId(telegramId)
                        .setReplyMarkup(twoKeys())
                        .setText("text")); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private ReplyKeyboard twoKeys() {
        var rkm = new ReplyKeyboardMarkup();
        KeyboardRow keyboardButtons = new KeyboardRow();
        keyboardButtons.add(Key.REGISTER_DOCTOR.getMessage());
        keyboardButtons.add(Key.FIND_DOCTOR.getMessage());
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

    @PostConstruct
    private void init() {
        stepServices = stepServiceList.stream()
                .collect(toMap(StepService::getStep, Functions.identity()));

        flowServices = flowServiceList.stream()
                .collect(toMap(FlowService::getFlow, Function.identity()));
    }
}
