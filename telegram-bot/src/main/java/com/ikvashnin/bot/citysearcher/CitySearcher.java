package com.ikvashnin.bot.citysearcher;

import lombok.extern.slf4j.Slf4j;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Component
public class CitySearcher {
    @Value("${endpoint.cities}")
    private String endPointCities;

    public List<City> search() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<City[]> response;
        try {
            response = restTemplate.getForEntity(endPointCities, City[].class);
        } catch (HttpClientErrorException e) {
            log.error(String.format("Статус запроса: %s", e.getStatusText()), e);
            throw new RuntimeException(e);
        }
        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }
}
