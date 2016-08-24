package com.hao.app.web.kong.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * Created by donghao on 16/8/20.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Api extends BaseModel{

    private String id;

    private String name;

    private String request_host;

    private String request_path;

    private String strip_request_path;

    private String preserve_host;

    private String upstream_url;

    private long createAt;

    private Integer size;

    private String offset;

}
