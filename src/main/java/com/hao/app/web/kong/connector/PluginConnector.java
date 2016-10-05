package com.hao.app.web.kong.connector;

import com.hao.app.web.kong.model.Consumer;
import com.hao.app.web.kong.model.EnablePlugins;
import com.hao.app.web.kong.model.Plugin;
import com.hao.app.web.kong.model.QueryRequest;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

/**
 * @author dongh38@ziroom
 * @Date 16/10/3
 * @Time 下午8:47
 */
public interface PluginConnector {

    @POST("/apis/{apiId}/plugins")
    Call<Plugin> add(@Path("apiId") String apiId,@Body Plugin plugin);

    @GET("/plugins/{pluginId}")
    Call<Plugin> queryOne(@Path("pluginId") String pluginId);

    @GET("/plugins")
    Call<QueryRequest<Plugin>> query(@QueryMap Map<String,Object> queryMap);

    @GET("/apis/{apiId}/plugins")
    Call<QueryRequest<Plugin>> querySpecApi(@Path("apiId") String apiId,@QueryMap Map<String,Object> queryMap);

    @PATCH("/apis/{apiId}/plugins/{pluginId}")
    Call<Plugin> update(@Path("apiId") String apiId,@Path("pluginId") String pluginId,@Body Plugin plugin);

    @DELETE("/apis/{apiId}/plugins/{pluginId}")
    Call<Void> delete(@Path("apiId") String apiId,@Path("pluginId") String pluginId);

    @GET("/plugins/enabled")
    Call<EnablePlugins> queryEnabled();

    @GET("/plugins/schema/{pluginName}")
    Call<String> pluginSchema(@Path("pluginName") String pluginName);
}
