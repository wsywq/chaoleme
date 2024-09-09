package com.ywq.common;

import lombok.Data;
import java.util.HashMap;
import java.util.Map;

/**
 * Common Response Template
 * @param <T>
 */
@Data
public class ResponseTemplate<T> {
    private Integer code;

    private String msg;

    private T data;

    private Map map = new HashMap();

    public static <T> ResponseTemplate<T> success(T object){
        ResponseTemplate<T> r = new ResponseTemplate<>();
        r.data = object;
        r.code = 200;
        return r;
    }

    public static <T> ResponseTemplate<T> error(String msg){
        ResponseTemplate<T> r = new ResponseTemplate<>();
        r.msg = msg;
        r.code = 400;
        return r;
    }

    public ResponseTemplate<T> add(String key, Object value){
        this.map.put(key, value);
        return this;
    }
}
