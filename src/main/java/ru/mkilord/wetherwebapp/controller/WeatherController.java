package ru.mkilord.wetherwebapp.controller;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.mkilord.wetherwebapp.exception.WeatherNotFoundException;
import ru.mkilord.wetherwebapp.model.Response;
import ru.mkilord.wetherwebapp.model.Weather;
import ru.mkilord.wetherwebapp.repository.RespHistoryRepository;
import ru.mkilord.wetherwebapp.service.WeatherService;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Component
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class WeatherController {
    WeatherService weatherService;
    RespHistoryRepository respHistoryRepository;

    public List<Response> getAllResponse() {
        return respHistoryRepository.findAll();
    }

    public void saveResponse(Response response) {
        respHistoryRepository.save(response);
    }

    public Weather getWeather(double lat, double lon, String langCode) throws WeatherNotFoundException {
        return weatherService.getWeather(lat, lon, langCode);
    }
}
