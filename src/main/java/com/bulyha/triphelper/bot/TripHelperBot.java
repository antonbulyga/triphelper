package com.bulyha.triphelper.bot;

import com.bulyha.triphelper.model.City;
import com.bulyha.triphelper.model.CityDto;
import com.bulyha.triphelper.service.CityService;
import com.bulyha.triphelper.utils.mapper.CityMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class TripHelperBot extends TelegramLongPollingBot {

    private TelegramBotsApi telegramBotsApi;
    private CityService cityService;
    private CityMapper cityMapper;

    public TripHelperBot(final TelegramBotsApi telegramBotsApi,
                         final CityService cityService,
                         final CityMapper cityMapper) {
        this.telegramBotsApi = telegramBotsApi;
        this.cityService = cityService;
        this.cityMapper = cityMapper;
    }

    @Override
    public void onUpdateReceived(final Update update) {
        String inMessage;
        String chatId;

        if (update.hasEditedMessage()) {
            inMessage = update.getEditedMessage().getText();
            chatId = update.getEditedMessage().getChatId().toString();
        } else {
            inMessage = update.getMessage().getText();
            chatId = update.getMessage().getChatId().toString();
        }

        switch (inMessage) {

            case "/start":
                sendMsg(chatId, "Welcome! You can see the control buttons below.");
                break;

            case "Available cities":
                cityService.findAll().stream()
                        .map(cityMapper::toDto)
                        .map(CityDto::getCityName)
                        .forEachOrdered(s -> sendMsg(chatId, s));
                break;

            case "Help":
                sendMsg(chatId, "Send me an email to bulyga.anton@gmail.com if you have any problems");
                break;

            default:
                Optional<City> cityOptional = cityService.findByCityName(inMessage);
                if (cityOptional.isPresent()) {
                    sendMsg(chatId, cityMapper.toDto(cityOptional.get()).toString());
                } else {
                    sendMsg(chatId, "I have no information about this city, sorry :(  ");
                }
                break;
        }
    }

    private void sendMsg(final String chatId, final String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        setButtons(sendMessage);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Error during sending message.", e);
        }
    }

    @Override
    public String getBotUsername() {
        return "@trip_helper94_bot";
    }

    @Override
    public String getBotToken() {
        return "1641679600:AAH4L_PJOzUI3Ui6QY7T_pyLLH94HuqmgN4";
    }

    private void setButtons(final SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("Available cities"));

        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton("Help"));

        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    @PostConstruct
    public void registryBot() {
        try {
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiRequestException e) {
            log.error("Error occurred during bot registration.", e);
        }
    }

}
