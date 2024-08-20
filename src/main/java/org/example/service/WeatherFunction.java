package org.example.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.models.WeatherData;
import org.example.repositories.WeatherRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Component
public class WeatherFunction {
    private final WeatherService weatherService;
    private final WeatherRepository weatherRepository;
    private final ObjectMapper objectMapper;

    public WeatherFunction(WeatherService weatherService, WeatherRepository weatherRepository, ObjectMapper objectMapper) {
        this.weatherService = weatherService;
        this.weatherRepository = weatherRepository;
        this.objectMapper = objectMapper;
    }

    public Mono<String> getWeather(String city, String auth) {
        return weatherService.getWeather(city, auth)
                .flatMap(response -> {
                    try {
                        JsonNode root = objectMapper.readTree(response);
                        WeatherData weatherData = new WeatherData();
                        weatherData.setCity(root.path("name").asText());
                        weatherData.setDescription(root.path("weather").get(0).path("description").asText());
                        weatherData.setTemperature(root.path("main").path("temp").asDouble());
                        weatherData.setHumidity(root.path("main").path("humidity").asDouble());

                        weatherRepository.saveWeatherData(weatherData);

                        return Mono.just("Weather data saved successfully for city: " + city);
                    } catch (Exception e) {
                        return Mono.error(new RuntimeException("Failed to process weather data", e));
                    }
                });
    }
}
