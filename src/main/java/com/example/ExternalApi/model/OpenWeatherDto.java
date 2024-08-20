package com.example.ExternalApi.model;

import lombok.Getter;

@Getter
public class OpenWeatherDto {
    private String name;
    private Main main;
    private Weather[] weather;

    @Getter
    public static class Main {
        private double temp;
        private double feels_like;
        private double temp_min;
        private double temp_max;
        private int pressure;
        private int humidity;
    }

    @Getter
    public static class Weather {
        private String description;
    }
}
