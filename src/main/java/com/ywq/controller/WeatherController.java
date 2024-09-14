package com.ywq.controller;

import com.ywq.common.ResponseTemplate;
import com.ywq.dto.WeatherDto;
import com.ywq.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/today")
    @Cacheable(cacheManager = "weatherCacheManager", cacheNames = "today", key = "#{location}")
    public ResponseTemplate<WeatherDto> getWeather(@RequestParam String location) {
        try {
            log.info("getTodayWeather location is:" + location);
            return ResponseTemplate.success(weatherService.getTodayWeather(location));
        } catch (Exception e) {
            log.error("getTodayWeatherFailed: " + e);
            e.printStackTrace();
            return ResponseTemplate.error("get weather failed");
        }
    }
}
