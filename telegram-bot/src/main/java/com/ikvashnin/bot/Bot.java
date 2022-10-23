package com.ikvashnin.bot;

import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.ikvashnin.bot.botUtils.returnStartMessage;
import static com.ikvashnin.bot.botUtils.returnUserMessage;

@Component
@NoArgsConstructor
public class Bot extends TelegramLongPollingBot {
    private static final Logger log = Logger.getLogger(TelegramBotApplication.class);
    @Value("${name}")
    private String botName;
    @Value("${token}")
    private String botToken;

    @Override
    public void onUpdateReceived(Update update) {
        log.debug("Receive new Update. updateID: " + update.getUpdateId());
        if (update.getMessage().getText().startsWith("/start")) {
            try {
                execute(returnStartMessage(update));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else {
            try {
                execute(returnUserMessage(update));
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public String getBotUsername() {
        log.debug("Bot name: " + botName);
        return botName;
    }

    @Override
    public String getBotToken() {
        log.debug("Bot token: " + botToken);
        return botToken;
    }
}