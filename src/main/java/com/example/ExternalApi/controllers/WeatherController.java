package com.example.ExternalApi.controllers;

import com.example.ExternalApi.ExternalApiService;
import com.example.ExternalApi.model.WeatherDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class WeatherController {

    private final ExternalApiService externalApiService;

    @GetMapping("/weather")
    public String getWeather(Model model) {
        WeatherDto weather = externalApiService.getExternalData();
        model.addAttribute("weather", weather);
        return "weather";
    }
}
