package com.hao.app.web.kong.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * @author dongh38@ziroom
 * @Date 16/10/3
 * @Time 下午8:30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Plugin extends BaseModel{

    private String id;

    private String name;

    private String api_id;

    private String consumer_id;

    private Map<String,Serializable> config;

    private Boolean enabled;

    private Long created_at;

    private Integer size;

    private String offset;
}
