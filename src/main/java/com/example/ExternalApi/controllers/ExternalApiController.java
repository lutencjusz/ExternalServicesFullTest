package com.example.ExternalApi.controllers;

import com.example.ExternalApi.ExternalApiService;
import com.example.ExternalApi.model.WeatherDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExternalApiController {

    private final ExternalApiService externalApiService;

    @GetMapping("/external")
    public ResponseEntity<WeatherDto> getExternalData() {
        return ResponseEntity.ok(externalApiService.getExternalData());
    }
}
