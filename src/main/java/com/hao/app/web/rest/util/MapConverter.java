package com.hao.app.web.rest.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * Created by donghao on 16/8/20.
 */
public class MapConverter {

    public MapConverter() {
    }

    public static Map<String,Object> toMap(Object object) {
        String jsonStr = JSON.toJSONString(object);
        return JSON.parseObject(jsonStr,new TypeReference<Map<String,Object>>(){

        });
    }
}
