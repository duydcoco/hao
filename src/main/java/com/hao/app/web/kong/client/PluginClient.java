package com.hao.app.web.kong.client;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hao.app.web.kong.connector.PluginConnector;
import com.hao.app.web.kong.model.EnablePlugins;
import com.hao.app.web.kong.model.Plugin;
import com.hao.app.web.kong.model.QueryRequest;
import com.hao.app.web.rest.util.Asserts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Retrofit;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * @author dongh38@ziroom
 * @Date 16/10/5
 * @Time 上午10:07
 */
@Component
@Slf4j
public class PluginClient extends BaseClient{

    private PluginConnector pluginConnector;

    @Inject
    public PluginClient(Retrofit retrofit) {
        super(retrofit);
        this.pluginConnector = retrofit.create(PluginConnector.class);
    }

    private Optional<String> getApiIdByPluginId(final String pluginId) {
        return queryOne(pluginId).map(Plugin::getApi_id);
    }

    private Boolean isPluginEnabled(String pluginName) {
        List<String> pluginList = enablePlugins();
        return Optional.ofNullable(pluginList).map(list -> list.contains(pluginName)).orElse(false);
    }

    public Optional<Plugin> add(String apiId,Plugin plugin) {
        if (!isPluginEnabled(plugin.getName())) {
            throw new IllegalArgumentException(String.format("插件 %s 未启用,请联系管理员",plugin.getName()));
        }
        Call<Plugin> call = pluginConnector.add(apiId, plugin);
        return Optional.of(handleResponse(call));
    }

    public Optional<Plugin> queryOne(String pluginId) {
        Call<Plugin> call = pluginConnector.queryOne(pluginId);
        return Optional.of(handleResponse(call));
    }


    public List<Plugin> query(Plugin plugin) {
        Preconditions.checkNotNull(plugin,"查询插件信息不能为空");
        Call<QueryRequest<Plugin>> call = pluginConnector.query(plugin.toMap());
        return Optional.ofNullable(handleResponse(call)).map(QueryRequest::getData).orElse(Lists.newArrayList());
    }

    public List<Plugin> querySpecApi(String apiId,Plugin plugin) {
        Call<QueryRequest<Plugin>> call = pluginConnector.querySpecApi(apiId, Optional.ofNullable(plugin).map(Plugin::toMap).orElse(Maps.newHashMap()));
        return Optional.ofNullable(handleResponse(call)).map(QueryRequest::getData).orElse(Lists.newArrayList());
    }

    public Optional<Plugin> querySpecApiAndPlugin(String apiId,String pluginName) {
        Asserts.checkNotBlank(apiId,"API ID不能为空");
        Asserts.checkNotBlank(pluginName,"插件名称不能为空");
        Call<QueryRequest<Plugin>> call = pluginConnector.querySpecApi(apiId, Plugin.builder().name(pluginName).build().toMap());
        return Optional.of(handleResponse(call)).map(result -> result.getData().stream().findFirst().orElse(null));
    }

    public Optional<Plugin> update(String pluginId,Plugin plugin) {
        String apiId = plugin.getApi_id();
        if (StringUtils.isBlank(apiId)) {
            return getApiIdByPluginId(pluginId).map(queryId -> handleResponse(pluginConnector.update(queryId,pluginId,plugin)));
        } else {
            Call<Plugin> call = pluginConnector.update(apiId, pluginId, plugin);
            return Optional.of(handleResponse(call));
        }

    }

    public List<String> enablePlugins() {
        Call<EnablePlugins> call = pluginConnector.queryEnabled();
        return Optional.ofNullable(handleResponse(call)).map(EnablePlugins::getEnabled_plugins).orElse(Lists.newArrayList());
    }

    public void delete(String apiId,String pluginId) {
        Asserts.checkNotBlank(pluginId,"插件ID不能为空");
        if (StringUtils.isBlank(apiId)) {
            getApiIdByPluginId(pluginId).ifPresent(queryApiId -> noContentResponse(pluginConnector.delete(apiId, pluginId)));
        } else {
            noContentResponse(pluginConnector.delete(apiId, pluginId));
        }
    }

    public void delete(String pluginId) {
        delete(null,pluginId);
    }

    public Integer totalSize(Plugin plugin) {
        Call<QueryRequest<Plugin>> call = pluginConnector.query(Optional.ofNullable(plugin).map(Plugin::toMap).orElse(Maps.newHashMap()));
        return Optional.ofNullable(handleResponse(call)).map(QueryRequest::getTotal).orElse(0);
    }


    @Override
    protected Logger getLogger() {
        return log;
    }
}
