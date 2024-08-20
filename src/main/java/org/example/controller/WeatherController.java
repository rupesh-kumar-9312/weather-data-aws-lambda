package org.example.controller;//package com.training.aws.weather_data.controller;

import org.example.service.WeatherFunction;
import org.example.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private WeatherFunction weatherFunction;

    @GetMapping("/{city}")
    public Mono<String> getWeatherData(@PathVariable String city, @RequestHeader("x-auth") String auth){
        return weatherFunction.getWeather(auth).apply(city);
    }

}
