package com.example.ExternalApi;

import com.example.ExternalApi.model.WeatherDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.sis.geometry.DirectPosition2D;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ExternalApiService {

    DirectPosition2D warsawPosition = new DirectPosition2D(52.237049, 21.017532);

    @Autowired
    private ExternalApiRepository externalApiRepository;

    public ExternalApiService(ExternalApiRepository externalApiRepository) {
        this.externalApiRepository = externalApiRepository;
    }

    public WeatherDto getExternalData() {
        return externalApiRepository.getExternalData(warsawPosition.getX(), warsawPosition.getY());
    }


}
