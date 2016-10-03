package com.hao.app.web.kong.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author dongh38@ziroom
 * @Date 16/10/3
 * @Time 下午8:38
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnablePlugins implements Serializable{

    private List<String> enabled_plugins;
}
