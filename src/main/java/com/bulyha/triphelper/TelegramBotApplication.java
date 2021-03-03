package com.bulyha.triphelper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class TelegramBotApplication {

    @PostConstruct
    public void init() {
        ApiContextInitializer.init();
    }

    public static void main(String[] args) {
        SpringApplication.run(TelegramBotApplication.class, args);
    }

}