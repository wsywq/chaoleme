package com.ywq.service.impl;

import com.ywq.dto.WeatherDto;
import com.ywq.remote.WeatherRemoteServiceCall;
import com.ywq.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class WeatherServiceImpl implements WeatherService {
    @Autowired
    private WeatherRemoteServiceCall weatherRemoteServiceCall;

    public WeatherDto getTodayWeather(String location){
        Map<String, String> currentWeather = weatherRemoteServiceCall.getCurrentWeather(location);
        Map<String, String> currentCity = weatherRemoteServiceCall.getCurrentCity(location);
        return WeatherDto.builder()
                .icon(currentWeather.get("icon"))
                .text(currentWeather.get("text"))
                .feelsLike(currentWeather.get("feelsLike"))
                .pressure(currentWeather.get("pressure"))
                .humidity(currentWeather.get("humidity"))
                .temp(currentWeather.get("temp"))
                .city(currentCity.get("country")+currentCity.get("adm1")+currentCity.get("adm2"))
                .build();
    }
}
