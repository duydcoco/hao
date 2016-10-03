package com.hao.app.web.kong.client;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.hao.app.web.kong.connector.ApiConnector;
import com.hao.app.web.kong.model.Api;
import com.hao.app.web.kong.model.QueryRequest;
import org.elasticsearch.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Retrofit;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Created by donghao on 16/8/20.
 */
@Component
public class ApiClient extends BaseClient{

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiClient.class);


    private ApiConnector apiConnector;




    @Inject
    public ApiClient(Retrofit retrofit) {
        super(retrofit);
        apiConnector = getRetrofit().create(ApiConnector.class);
    }


    public Optional<Api> add(Api api) {
        Call<Api> add = apiConnector.add(api);
        return Optional.of(handleResponse(add));
    }


    public List<Api> queryAll(Api api) {
        Call<QueryRequest<Api>> query = apiConnector.query(
            Optional.ofNullable(api).map(Api::toMap).orElse(Maps.newHashMap()));
        return Optional.ofNullable(handleResponse(query))
                .map(QueryRequest::getData).orElse(Lists.newArrayList());
    }

    public Integer toTalSize(Api api) {
        Call<QueryRequest<Api>> call = apiConnector.query(
                Optional.ofNullable(api).map(Api::toMap).orElse(Maps.newHashMap()));
        return Optional.ofNullable(handleResponse(call)).map(QueryRequest::getTotal).orElse(0);
    }

    public Optional<Api> queryOne(String apiId) {
        Call<Api> call = apiConnector.queryOne(apiId);
        return Optional.of(handleResponse(call));
    }

    public Optional<Api> update(Api api) {
        Preconditions.checkNotNull(api,"API 信息不能为空");
        Preconditions.checkNotNull(api.getId(),"API ID不能为空");
        Call<Api> call = apiConnector.update(api.getId(), api);
        return Optional.of(handleResponse(call));
    }


    public void delete(String apiId) {
        noContentResponse(apiConnector.delete(apiId));

    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }
}
