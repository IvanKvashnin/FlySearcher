package com.ikvashnin.bot;

import com.ikvashnin.bot.citysearcher.Cases;
import com.ikvashnin.bot.citysearcher.City;
import com.ikvashnin.bot.citysearcher.CitySearcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.telegram.telegrambots.starter.TelegramBotStarterConfiguration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@SpringBootTest
class TelegramBotApplicationTests {
    @MockBean
    TelegramBotStarterConfiguration telegramBotStarterConfiguration;
    @Autowired
    CitySearcher citySearcher;

    @Test
    void endingOfTheWord() {
        String telegramMsg = "Я полечу в Стамбул из Москвы, а потом в Баку";
        String[] splitTelegramMsg = telegramMsg.replaceAll("[^A-Za-zА-Яа-я0-9]", " ").split("\\s");
        List<Cases> cases = citySearcher.search().stream().map(City::getCases).toList();
        ArrayList<String> casesToString = new ArrayList<>();
        for (Cases c : cases) {
            if (c != null) {
                casesToString.add(c.getDa());
                casesToString.add(c.getPr());
                casesToString.add(c.getRo());
                casesToString.add(c.getSu());
                casesToString.add(c.getTv());
                casesToString.add(c.getVi());
            }
        }
        ArrayList<String> splitCases = new ArrayList<>();
        for (String sc : casesToString) {
            if (sc != null)
                splitCases.add(sc.substring(sc.indexOf(" ") + 1));
        }

        HashSet<String> citiesFromMsg = new HashSet<>();
        for (String sc : splitCases) {
            for (String msg : splitTelegramMsg) {
                if (sc.equals(msg)) {
                    citiesFromMsg.add(sc);
                }
            }
        }
        citiesFromMsg.forEach(System.out::println);
    }
}



