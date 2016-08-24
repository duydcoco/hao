package com.hao.app.web.kong.connector;

import com.hao.app.web.kong.model.Api;
import com.hao.app.web.kong.model.QueryRequest;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

/**
 * Created by donghao on 16/8/20.
 */
public interface ApiConnector {

    @POST("/apis")
    Call<Api> add(@Body Api newApi);

    @GET("/apis")
    Call<QueryRequest<Api>> query(@QueryMap Map<String,Object> queryInfo);

    @GET("/apis/{apiId}")
    Call<Api> queryOne(@Path("apiId") String apiId);

    @PATCH("/apis/{apiId}")
    Call<Api> update(@Path("apiId")String apiId,@Body  Api newApi);

    @DELETE("apis/{apiId}")
    Call<Void> delete(@Path("apiId")String apiId);

}
