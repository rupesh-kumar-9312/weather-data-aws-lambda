package org.example.controller;


import org.example.service.WeatherFunction;
import org.example.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;


@RestController
public class PingController {
    @Autowired
    private WeatherService weatherService;

    @Autowired
    private WeatherFunction weatherFunction;

    @RequestMapping(path = "/ping/{city}", method = RequestMethod.GET)
    public Mono<String> getWeatherData(@PathVariable String city){
        return weatherFunction.getWeather().apply(city);
    }

    @RequestMapping(path = "/ping", method = RequestMethod.GET)
    public Map<String, String> ping() {
        Map<String, String> pong = new HashMap<>();
        pong.put("pong", "Hello, World!");
        return pong;
    }

}
