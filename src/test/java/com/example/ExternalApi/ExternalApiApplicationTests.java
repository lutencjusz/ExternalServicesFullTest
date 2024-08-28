    package com.example.ExternalApi;

    import com.example.ExternalApi.model.OpenWeatherDto;
    import com.example.ExternalApi.model.WeatherDto;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import io.github.cdimascio.dotenv.Dotenv;
    import org.junit.jupiter.api.Assertions;
    import org.junit.jupiter.api.Test;
    import org.mockito.InjectMocks;
    import org.mockito.Mock;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
    import org.springframework.boot.test.context.SpringBootTest;
    import org.springframework.test.web.servlet.MockMvc;
    import org.springframework.test.web.servlet.MvcResult;

    import static org.hamcrest.Matchers.containsString;
    import static org.junit.jupiter.api.Assertions.assertEquals;
    import static org.junit.jupiter.api.Assertions.assertNotNull;
    import static org.mockito.ArgumentMatchers.eq;
    import static org.mockito.Mockito.when;
    import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
    import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
    import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ExternalApiApplicationTests {

    Dotenv dotenv = Dotenv.load();

    @Mock
    private ExternalApiRepository externalApiRepository;

    @InjectMocks
    private ExternalApiService externalApiService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldGetExternalData() throws Exception {
        mockMvc.perform(get("/external"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.temperature").isNumber())
                .andExpect(jsonPath("$.feelsLike").isNumber())
                .andExpect(jsonPath("$.minTemperature").isNumber())
                .andExpect(jsonPath("$.maxTemperature").isNumber())
                .andExpect(jsonPath("$.pressure").isNumber())
                .andExpect(jsonPath("$.humidity").isNumber())
                .andExpect(jsonPath("$.description").isString());
    }

    @Test
    void shouldGetWeather() throws Exception {
        String response = mockMvc.perform(get("/external"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.temperature").isNumber())
                .andExpect(jsonPath("$.feelsLike").isNumber())
                .andExpect(jsonPath("$.minTemperature").isNumber())
                .andExpect(jsonPath("$.maxTemperature").isNumber())
                .andExpect(jsonPath("$.pressure").isNumber())
                .andExpect(jsonPath("$.humidity").isNumber())
                .andExpect(jsonPath("$.description").isString())
                .andReturn().getResponse().getContentAsString();
        WeatherDto actualWeather = new ObjectMapper().readValue(response, WeatherDto.class);
        mockMvc.perform(get("/weather"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<span>" + actualWeather.getTemperature() + " °C</span>")))
                .andExpect(content().string(containsString("<span>" + actualWeather.getFeelsLike() + " °C</span>")))
                .andExpect(content().string(containsString("<span>" + actualWeather.getMinTemperature() + " °C</span>")))
                .andExpect(content().string(containsString("<span>" + actualWeather.getMaxTemperature() + " °C</span>")))
                .andExpect(content().string(containsString("<span>" + actualWeather.getPressure() + " hPa</span>")))
                .andExpect(content().string(containsString("<span>" + actualWeather.getHumidity() + " %</span>")))
                .andExpect(content().string(containsString("<span class=\"label\">Podsumowanie:</span>")));
    }

    @Test
    void shouldGetAnswerForQuestion() throws Exception {

        MvcResult result = mockMvc.perform(get("/chat?question=Jaka jest pogoda?"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String response = result.getResponse().getContentAsString();
        Assertions.assertFalse(response.isEmpty());

        mockMvc.perform(get("/chat"))
                .andDo(print())
                .andExpect(content().string(containsString("Brak wymaganego parametru: question")))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetDataFromExternalDataRepository() {
        String apiKey = dotenv.get("API_KEY");
        final double lat = 52.52;
        final double lon = 13.405;

        OpenWeatherDto mockOpenWeatherDto = new OpenWeatherDto();
        mockOpenWeatherDto.setMain(new OpenWeatherDto.Main());
        mockOpenWeatherDto.getMain().setTemp(10.0);
        mockOpenWeatherDto.getMain().setFeels_like(8.0);
        mockOpenWeatherDto.getMain().setTemp_min(5.0);
        mockOpenWeatherDto.getMain().setTemp_max(15.0);
        mockOpenWeatherDto.getMain().setPressure(1013);
        mockOpenWeatherDto.getMain().setHumidity(75);
        mockOpenWeatherDto.setWeather(new OpenWeatherDto.Weather[]{new OpenWeatherDto.Weather()});
        mockOpenWeatherDto.getWeather()[0].setDescription("clear sky");

        WeatherDto mockWeatherDto = WeatherDto.builder()
                .temperature((float) mockOpenWeatherDto.getMain().getTemp())
                .feelsLike((float) mockOpenWeatherDto.getMain().getFeels_like())
                .minTemperature((float) mockOpenWeatherDto.getMain().getTemp_min())
                .maxTemperature((float) mockOpenWeatherDto.getMain().getTemp_max())
                .pressure(mockOpenWeatherDto.getMain().getPressure())
                .humidity(mockOpenWeatherDto.getMain().getHumidity())
                .description(mockOpenWeatherDto.getWeather()[0].getDescription())
                .build();

        when(externalApiRepository.getExternalData(lat, lon)).thenReturn(mockWeatherDto);

        when(externalApiRepository.getGetDataFromExternalData(
                eq(ExternalApiRepository.WEATHER_GET_EXTERNAL_DATA_URI),
                eq(WeatherDto.class),
                eq(lat),
                eq(lon),
                eq(apiKey)))
                .thenReturn(mockWeatherDto);

        // Wywołanie metody do przetestowania
        WeatherDto result = externalApiRepository.getExternalData(lat, lon);

        // Weryfikacja wyników
        assertNotNull(result);
        assertEquals(mockOpenWeatherDto.getMain().getTemp(), result.getTemperature());
        assertEquals(mockOpenWeatherDto.getMain().getFeels_like(), result.getFeelsLike());
        assertEquals(mockOpenWeatherDto.getMain().getTemp_min(), result.getMinTemperature());
        assertEquals(mockOpenWeatherDto.getMain().getTemp_max(), result.getMaxTemperature());
        assertEquals(mockOpenWeatherDto.getMain().getPressure(), result.getPressure());
        assertEquals(mockOpenWeatherDto.getMain().getHumidity(), result.getHumidity());
        assertEquals(mockOpenWeatherDto.getWeather()[0].getDescription(), result.getDescription());
    }
}
