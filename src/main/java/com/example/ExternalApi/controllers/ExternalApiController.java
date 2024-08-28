package com.example.ExternalApi.controllers;

import com.example.ExternalApi.ExternalApiService;
import com.example.ExternalApi.LlamaChatService;
import com.example.ExternalApi.model.WeatherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExternalApiController {

    @Autowired
    private ExternalApiService externalApiService;

    @Autowired
    private LlamaChatService llamaChatService;

    @GetMapping("/external")
    public ResponseEntity<WeatherDto> getExternalData() {
        return ResponseEntity.ok(externalApiService.getExternalData());
    }

    @GetMapping("/chat")
    public String getAnswerForQuestion(@RequestParam("question") String question) {
        WeatherDto weatherDto = externalApiService.getExternalData();
        String data = "Dane pogodowe: " + weatherDto.toString();
        return llamaChatService.getAnswerForQuestion(question + data);
    }
}
