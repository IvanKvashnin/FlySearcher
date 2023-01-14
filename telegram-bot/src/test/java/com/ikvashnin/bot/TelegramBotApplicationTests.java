package com.ikvashnin.bot;

import com.ikvashnin.bot.citysearcher.Cases;
import com.ikvashnin.bot.citysearcher.City;
import com.ikvashnin.bot.citysearcher.CitySearcher;
import com.ikvashnin.bot.dateparser.DateParser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.telegram.telegrambots.starter.TelegramBotStarterConfiguration;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TelegramBotApplicationTests {
    @MockBean
    TelegramBotStarterConfiguration telegramBotStarterConfiguration;
    @Autowired
    CitySearcher citySearcher;

    @Autowired
    DateParser dateParser;


    @Test
    public void test() {
        String telegramMsg = "Я полечу в Стамбул из Москвы, а потом в Баку";
        String[] words = telegramMsg.replaceAll("[^A-Za-zА-Яа-я0-9]", " ").split("\\s");
        ArrayList<City> cities = new ArrayList<>();
        for (String word : words) {
            var city = citySearcher.search(word);
            if (city != null) {
                cities.add(city);
            }
        }
        assertThat(cities).size().isEqualTo(2);
    }

    @Test
    public void cityTest() {
        var city = City.builder().name("Москва")
                .code("MOW")
                .cases(Cases.builder()
                        .da("Москве")
                        .pr("Москве")
                        .ro("Москвы")
                        .su("Москва")
                        .tv("Москвой")
                        .vi("в Москву")
                        .build())
                .build();
        assertThat(city.isCityName("Стамбул")).isFalse();
        assertThat(city.isCityName("Москвой")).isTrue();
    }

    @Test
    public void dateTest() {
        String msg = "Я полечу 21.03.2022 в Москву";
        assertThat(LocalDate.of(2022, 3, 21)).isEqualTo(DateParser.parse("21.03.2022"));
        assertThat(LocalDate.of(2022, 6, 21)).isEqualTo(DateParser.parse("21 июня 2022"));
        assert DateParser.parse("я") == null;
    }
}



