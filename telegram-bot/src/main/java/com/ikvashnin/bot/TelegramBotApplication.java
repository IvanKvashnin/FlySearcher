package com.ikvashnin.bot;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TelegramBotApplication {
    public static void main(String[] args) {
        BasicConfigurator.configure();
        SpringApplication.run(TelegramBotApplication.class, args);
    }

}
