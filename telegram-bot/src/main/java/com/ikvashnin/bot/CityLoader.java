package com.ikvashnin.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
public class CityLoader {
    public List<City> load() {
        RestTemplate restTemplate = new RestTemplate();
        String urlCityRequest = "https://api.travelpayouts.com/aviasales_resources/v3/cities.json?locale=ru";
        ResponseEntity<City[]> response = null;
        try {
            response = restTemplate.getForEntity(urlCityRequest, City[].class);
        } catch (HttpClientErrorException e) {
            log.error(String.format("Статус запроса: %s", e.getStatusText()), e);
        }

        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }
}
