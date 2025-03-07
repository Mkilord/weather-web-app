package ru.mkilord.wetherwebapp.config;

import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Configuration
@FieldDefaults(level = PRIVATE)
public class WeatherConfig {
    @Value("${weather.api.key}")
    String apiKey;
    @Value("${weather.api.url}")
    String apiUrl = "http://api.openweathermap.org/data/2.5/weather";

    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl(apiUrl).build();
    }
}
