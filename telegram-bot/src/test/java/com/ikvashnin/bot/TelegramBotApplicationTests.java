package com.ikvashnin.bot;

import com.ikvashnin.bot.citysearcher.Cases;
import com.ikvashnin.bot.citysearcher.City;
import com.ikvashnin.bot.citysearcher.CitySearcher;
import com.ikvashnin.bot.dateparser.DateParser;
import com.ikvashnin.bot.ticketSearcher.TicketSearcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.telegram.telegrambots.starter.TelegramBotStarterConfiguration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TelegramBotApplicationTests {
    @MockBean
    TelegramBotStarterConfiguration telegramBotStarterConfiguration;
    @Autowired
    CitySearcher citySearcher;

    @Autowired
    DateParser dateParser;

    @Autowired
    TicketSearcher ticketSearcher;

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
        String msg = "Я полечу 21.03.2022 в Москвa";
        assertThat(LocalDate.of(2022, 3, 21)).isEqualTo(dateParser.parse("21.03.2022"));
        assertThat(LocalDate.of(2022, 6, 21)).isEqualTo(dateParser.parse("21 июня 2022"));
        assert dateParser.parse("я") == null;
    }

    @Test
    public void ticketSearcherTest() {
        var words = "Я полечу 21.03.2022 из Москвы в Стамбул и обратно 01.04.2022".split(" ");
        var cities = Arrays.stream(words).map(word -> citySearcher.search(word)).filter(Objects::nonNull).toList();
        assertThat(cities.size()).isEqualTo(2);
        assertThat(cities.get(0).getName()).isEqualTo("Москва");
        assertThat(cities.get(1).getName()).isEqualTo("Стамбул");
        var dates = Arrays.stream(words).map(date -> dateParser.parse(date)).filter(Objects::nonNull).toList();
        assertThat(dates.get(0)).isEqualTo(dateParser.parse("21.03.2022"));
        assertThat(dates.get(1)).isEqualTo(dateParser.parse("01.04.2022"));
        var ticket = ticketSearcher.search("Я полечу 21.03.2022 из Москвы в Стамбул и обратно 01.04.2022");
        // TODO: 29.01.2023 дописать проверку
        assertThat(List.of("Москва", "Стамбул")).isEqualTo(ticket);
    }
}



