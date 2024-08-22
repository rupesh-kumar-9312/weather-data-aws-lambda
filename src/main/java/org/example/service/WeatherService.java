package org.example.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.models.WeatherData;
import org.example.repositories.WeatherRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class WeatherService {

    private final WeatherRepository weatherRepository;
    private final ObjectMapper objectMapper;
    private final WebClient webClient;

    public WeatherService(WeatherRepository weatherRepository, ObjectMapper objectMapper, WebClient.Builder webClientBuilder) {
        this.weatherRepository = weatherRepository;
        this.objectMapper = objectMapper;
        this.webClient = webClientBuilder.baseUrl("https://api.openweathermap.org/data/2.5/").build();
    }

    public Mono<String> getWeather(String city, String auth) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("weather")
                        .queryParam("q", city)
                        .queryParam("appid", auth)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }


    public Mono<String> saveWeatherData(String city, String auth) {
        return getWeather(city, auth)
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
