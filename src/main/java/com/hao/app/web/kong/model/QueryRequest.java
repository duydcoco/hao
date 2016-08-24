package com.hao.app.web.kong.model;

import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by donghao on 16/8/20.
 */

@Data
public class QueryRequest<T extends Serializable> implements Serializable {

    private Integer total;

    private List<T> data = Lists.newArrayList();


}
