package com.ikvashnin.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class BotUtils {

    public static SendMessage returnStartMessage(Update update) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId());
        message.setText("Hello. This is start message");
        return message;
    }
    public static SendMessage returnUserMessage(Update update) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId());
        message.setText(update.getMessage().getText());
        return message;
    }
}

