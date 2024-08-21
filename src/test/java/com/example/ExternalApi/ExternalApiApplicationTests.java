package com.example.ExternalApi;

import com.example.ExternalApi.model.WeatherDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ExternalApiApplicationTests {

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
                .andExpect(content().string(containsString("<span>" + actualWeather.getDescription() + "</span>")));
    }
}
