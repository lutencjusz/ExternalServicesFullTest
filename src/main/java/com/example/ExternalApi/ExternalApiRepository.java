package com.example.ExternalApi;

import com.example.ExternalApi.model.OpenWeatherDto;
import com.example.ExternalApi.model.WeatherDto;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Repository
public class ExternalApiRepository {

    Dotenv dotenv = Dotenv.load();

    private static final String URL = "https://api.openweathermap.org/data/2.5/";
    String API_KEY = dotenv.get("API_KEY");

    RestTemplate restTemplate = new RestTemplate();

    private <T> T getGetDataFromExternalData(String url, Class<T> responseType, Object... objects) {
        return restTemplate.getForObject(URL + url, responseType, objects);

    }

    @Cacheable("externalData")
    public WeatherDto getExternalData(double lat, double lon) {
        OpenWeatherDto openWeatherDto = getGetDataFromExternalData("weather?lat={lat}&lon={lom}&appid={apiKey}&lang=pl&units=metric", OpenWeatherDto.class, lat, lon, API_KEY);
        return WeatherDto.builder()
                .temperature((float) openWeatherDto.getMain().getTemp())
                .feelsLike((float) openWeatherDto.getMain().getFeels_like())
                .minTemperature((float) openWeatherDto.getMain().getTemp_min())
                .maxTemperature((float) openWeatherDto.getMain().getTemp_max())
                .pressure(openWeatherDto.getMain().getPressure())
                .humidity(openWeatherDto.getMain().getHumidity())
                .description(openWeatherDto.getWeather()[0].getDescription())
                .build();
    }
}
