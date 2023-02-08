package com.ikvashnin.bot;

import com.ikvashnin.bot.ticketSearcher.TicketSearcher;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.ikvashnin.bot.BotUtils.returnStartMessage;

@NoArgsConstructor
@Slf4j
@Service
public class Bot extends TelegramLongPollingBot {
    @Value("${bot.name}")
    private String botName;
    @Value("${bot.token}")
    private String botToken;
    @Autowired
    TicketSearcher ticketSearcher;


    @Override
    public void onUpdateReceived(Update update) {
        log.debug("Receive new Update. updateID: " + update.getUpdateId());
        try {
            if (update.getMessage().getText().startsWith("/start")) {
                execute(returnStartMessage(update));
            } else {
                execute(returnTickerSearchResult(update));
            }
        } catch (TelegramApiException e) {
            log.error(String.format("ID пользовтеля: %s, Сообщение пользователя: %s", update.getMessage().getFrom().getId(), update.getMessage().getText()), e);
        }
    }

    public SendMessage returnTickerSearchResult(Update update) {
        var searchText = update.getMessage().getText();
        var tickets = ticketSearcher.search(searchText);
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId());
        message.setText(String.join(";", tickets.stream().map(Object::toString).toList()));
        return message;

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