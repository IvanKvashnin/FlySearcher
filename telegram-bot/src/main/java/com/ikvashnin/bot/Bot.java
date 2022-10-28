package com.ikvashnin.bot;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.ikvashnin.bot.BotUtils.returnStartMessage;
import static com.ikvashnin.bot.BotUtils.returnUserMessage;

@Component
@NoArgsConstructor
@Slf4j
public class Bot extends TelegramLongPollingBot {
    @Value("${name}")
    private String botName;
    @Value("${token}")
    private String botToken;

    @Override
    public void onUpdateReceived(Update update) {
        log.debug("Receive new Update. updateID: " + update.getUpdateId());
        try {
            if (update.getMessage().getText().startsWith("/start")) {
                execute(returnStartMessage(update));
            } else {
                execute(returnUserMessage(update));
            }
        } catch (TelegramApiException e) {
            log.error(String.format("ID пользовтеля: %s, Сообщение пользователя: %s",
                    update.getMessage().getFrom().getId(), update.getMessage().getText()), e);
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