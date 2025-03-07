package ru.mkilord.wetherwebapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.mkilord.wetherwebapp.config.WeatherConfig;
import ru.mkilord.wetherwebapp.exception.WeatherNotFoundException;
import ru.mkilord.wetherwebapp.model.Weather;

import static java.util.Objects.nonNull;
import static lombok.AccessLevel.PRIVATE;

@Service
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class WeatherService {

    WebClient webClient;
    WeatherConfig config;

    public Weather getWeather(double lat, double lon, String langCode) throws WeatherNotFoundException {
        var response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("lat", lat)
                        .queryParam("lon", lon)
                        .queryParam("appid", config.getApiKey())
                        .queryParam("units", "metric")
                        .queryParam("lang", langCode)
                        .build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        if (nonNull(response) && response.has("weather") && response.has("main")) {
            var weatherNode = response.get("weather").get(0);
            var mainNode = response.get("main");

            return new Weather(
                    weatherNode.get("main").asText(),
                    weatherNode.get("description").asText(),
                    mainNode.get("temp").asDouble(),
                    mainNode.get("feels_like").asDouble(),
                    weatherNode.get("icon").asText()
            );
        }
        throw new WeatherNotFoundException();
    }
}
