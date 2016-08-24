package com.hao.app.web.kong.model;

import com.hao.app.web.rest.util.MapConverter;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by donghao on 16/8/20.
 */
public abstract class BaseModel implements Serializable{

    public Map<String,Object> toMap() {
        return MapConverter.toMap(this);
    }
}
