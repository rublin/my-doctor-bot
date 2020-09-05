package com.mydoctor.mydoctorbot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@Slf4j
@Configuration
public class AppConfiguration {

    static {
        ApiContextInitializer.init();
    }

    @Value("${bot.token}")
    String token;

    @Bean
    public TelegramBotConnection telegramBotConnection() {
        return new TelegramBotConnection(token);
    }

    @Bean
    public TelegramBotsApi telegramBotsApi() {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(telegramBotConnection());
        } catch (TelegramApiRequestException e) {
            log.error("Failed to register bot: ", e);
        }
        return telegramBotsApi;
    }
}
