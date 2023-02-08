package com.ikvashnin.bot.ticketSearcher;

import com.ikvashnin.bot.citysearcher.CitySearcher;
import com.ikvashnin.bot.dateparser.DateParser;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class TicketSearcher {
    private final CitySearcher citySearcher;
    private final DateParser dateParser;

    public TicketSearcher(CitySearcher citySearcher, DateParser dateParser) {
        this.citySearcher = citySearcher;
        this.dateParser = dateParser;
    }

    public List<Object> search(String text) {
        var words = text.split(" ");
        var cities = Arrays.stream(words).map(citySearcher::search).filter(Objects::nonNull).toList();
        var dates = Arrays.stream(words).map(dateParser::parse).filter(Objects::nonNull).toList();;
        return List.of(cities, dates);
    }
}
