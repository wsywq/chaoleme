package com.ywq.remote;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ywq.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

@Component
@Slf4j
public class WeatherRemoteServiceCall {

    @Value("${remote.hefeng-weather.weather}")
    private String WEATHER_URL;

    @Value("${remote.hefeng-weather.city}")
    private String CITY_URL;

    @Value(("${remote.hefeng-weather.privateKey}"))
    private String PRIVATE_KEY;

    public Map<String, String> getCurrentWeather(String location) {
        String weaURL = WEATHER_URL + "/now?location=" + location + "&key=" + PRIVATE_KEY;
        try {
            JSONObject jsonObject = HttpUtils.hefengWeatherHttpCall(weaURL);
            return JSONObject.parseObject(jsonObject.getJSONObject("now").toString(), Map.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public Map<String, String> getCurrentCity(String location) {
        String geoURL = CITY_URL + "/lookup?location=" + location + "&key=" + PRIVATE_KEY;
        try {
            JSONObject jsonObject = HttpUtils.hefengWeatherHttpCall(geoURL);
            JSONArray locationArray = jsonObject.getJSONArray("location");
            if (locationArray != null && locationArray.size() > 0) {
                // 获取数组的第一个元素
                return JSONObject.parseObject(locationArray.getJSONObject(0).toString(), Map.class);
            } else {
                return new HashMap<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }


}
