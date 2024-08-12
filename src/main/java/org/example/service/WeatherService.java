package org.example.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class WeatherService {

    @Value("${config.api.key}")
    private String apiKey;

    private final WebClient webClient;

    public WeatherService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.openweathermap.org/data/2.5/").build();
    }

    public Mono<String> getWeather(String city) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("weather")
                        .queryParam("q", city)
                        .queryParam("appid", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }
}
