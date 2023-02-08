package com.ikvashnin.bot.dateparser;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Locale.forLanguageTag;
import static java.util.regex.Pattern.matches;

@Component
public class DateParser {
    public LocalDate parse(String date) {
        LocalDate localDate;
        if (matches("\\d{2}.\\d{2}.\\d{4}", date)) {
            localDate = LocalDate.parse(date, ofPattern("dd.MM.yyy"));
        } else if (matches("\\d{2}\\s[а-яА-Я]*\\s\\d{4}", date)) {
            localDate = LocalDate.parse(date, ofPattern("dd MMMM yyyy", forLanguageTag("ru-RU")));
        } else {
            return null;
        }
        return localDate;
    }
}
