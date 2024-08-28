package com.example.ExternalApi.model;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class WeatherDto {
    private float temperature;
    private float feelsLike;
    private float minTemperature;
    private float maxTemperature;
    private int pressure;
    private int humidity;
    private String description;

    public WeatherDto() {
    }
}
