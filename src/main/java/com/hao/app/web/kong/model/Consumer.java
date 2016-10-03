package com.hao.app.web.kong.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dongh38@ziroom
 * @Date 16/10/3
 * @Time 下午6:01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Consumer extends BaseModel{

    private String id;

    private String username;

    private String custom_id;

    private Long created_at;

    private Integer size;

    private String offset;


}
