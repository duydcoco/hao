package com.hao.app.web.kong.connector;

import com.hao.app.web.kong.model.Consumer;
import com.hao.app.web.kong.model.QueryRequest;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

/**
 * @author dongh38@ziroom
 * @Date 16/10/3
 * @Time 下午8:47
 */
public interface ConsumerConnector {

    @POST("/consumers")
    Call<Consumer> add(@Body Consumer consumer);

    @GET("/consumers")
    Call<QueryRequest<Consumer>> query(@QueryMap Map<String,Object> queryMap);

    @GET("/consumers/{consumerId}")
    Call<Consumer> queryOne(@Path("consumerId") String consumerId);

    @PATCH("/consumers/{consumerId}")
    Call<Consumer> update(@Path("consumerId") String consumerId,@Body Consumer consumer);

    @DELETE("/consumers/{consumerId}")
    Call<Void> delete(@Path("consumerId") String consumerId) ;
}
