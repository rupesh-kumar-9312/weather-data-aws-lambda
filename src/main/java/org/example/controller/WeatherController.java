package org.example.controller;//package com.training.aws.weather_data.controller;

import org.example.service.WeatherFunction;
import org.example.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private WeatherFunction weatherFunction;

    @GetMapping("/{city}")
    public Mono<String> getWeatherData(@PathVariable String city){
        return weatherFunction.getWeather().apply(city);
    }

}
