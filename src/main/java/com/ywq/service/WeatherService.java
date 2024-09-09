package com.ywq.service;

import com.ywq.dto.WeatherDto;
import org.springframework.stereotype.Service;


public interface WeatherService {

    public WeatherDto getTodayWeather(String location);
}
