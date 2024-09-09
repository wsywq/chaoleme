package com.ywq.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeatherDto {

    // 天气图标
    private String icon;

    // 天气描述
    private String text;

    // 温度
    private String temp;

    // 体感温度
    private String feelsLike;

    // 湿度
    private String humidity;

    // 气压
    private String pressure;

    // 城市
    private String city;
}
