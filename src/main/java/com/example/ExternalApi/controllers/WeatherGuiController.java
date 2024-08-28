package com.example.ExternalApi.controllers;

import com.example.ExternalApi.ExternalApiService;
import com.example.ExternalApi.LlamaChatService;
import com.example.ExternalApi.model.WeatherDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class WeatherGuiController {

    @Autowired
    private ExternalApiService externalApiService;

    @Autowired
    private LlamaChatService llamaChatService;

    @GetMapping("/weather")
    public String getWeather(Model model) {

        String WEATHER_COMMENT = "Skomentuj bieżącą pogodę w maksymalnie 20 słowach, uwzględniając następujące dane:";

        WeatherDto weather = externalApiService.getExternalData();
        weather.setDescription(llamaChatService.getAnswerForQuestion(WEATHER_COMMENT + weather));
        model.addAttribute("weather", weather);
        return "weather";
    }
}
