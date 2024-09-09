package com.ywq.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;

@Component
@Slf4j
public class HttpUtils {

    /**
     * 和风天气开发文档 https://dev.qweather.com/docs/api/
     * 返回数据是JSON格式并进行了Gzip压缩
     *
     * @param API url
     * @return json
     */
    public static JSONObject hefengWeatherHttpCall(String url) {
        try {
            URL remoteURL = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) remoteURL.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(1000);
            conn.setConnectTimeout(1000);
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("contentType", "application/json;charset=utf-8");
            return processGZIPResponseData(conn.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static JSONObject processGZIPResponseData(InputStream inputStream) {
        StringBuilder sb = new StringBuilder();
        InputStream input;
        BufferedReader br = null;

        try {
            input = new GZIPInputStream(inputStream);
            br = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            br.close();
            input.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info(sb.toString());
        return JSON.parseObject(sb.toString());
    }

    public static JSONObject processResponseData(HttpResponse response) {
        try {
            // Get the response content as an InputStream
            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();
//            GZIPInputStream inputStream = new GZIPInputStream(entity.getContent());
            BufferedReader reader = new BufferedReader(new InputStreamReader(content, StandardCharsets.UTF_8));
            // Read the InputStream
//            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            StringBuilder responseContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            return JSONObject.parseObject(responseContent.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
