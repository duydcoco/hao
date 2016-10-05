package com.hao.app.web.kong.client;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hao.app.web.kong.connector.ConsumerConnector;
import com.hao.app.web.kong.model.Consumer;
import com.hao.app.web.kong.model.QueryRequest;
import com.hao.app.web.rest.util.Asserts;
import lombok.extern.slf4j.Slf4j;
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
 * @Time 上午10:06
 */
@Component
@Slf4j
public class ConsumerCliet extends BaseClient{

    private ConsumerConnector consumerConnector;


    @Inject
    public ConsumerCliet(Retrofit retrofit) {
        super(retrofit);
        this.consumerConnector = retrofit.create(ConsumerConnector.class);
    }


    public Optional<Consumer> add(Consumer consumer) {
        Call<Consumer> call = consumerConnector.add(consumer);
        return Optional.ofNullable(handleResponse(call));
    }

    public List<Consumer> query(Consumer consumer) {
        Call<QueryRequest<Consumer>> call = consumerConnector.query(Optional.ofNullable(consumer).map(Consumer::toMap).orElse(Maps.newHashMap()));
        return Optional.ofNullable(handleResponse(call)).map(QueryRequest::getData).orElse(Lists.newArrayList());
    }

    public int totalSize(Consumer consumer) {
        Call<QueryRequest<Consumer>> call = consumerConnector.query(
                Optional.ofNullable(consumer).map(Consumer::toMap).orElse(Maps.newHashMap())
        );
        return Optional.ofNullable(handleResponse(call)).map(QueryRequest::getTotal).orElse(0);
    }

    public Optional<Consumer> queryOne(String consumerId) {
        Call<Consumer> call = consumerConnector.queryOne(consumerId);
        return Optional.ofNullable(handleResponse(call));
    }

    public Optional<Consumer> update(Consumer consumer) {
        Preconditions.checkNotNull(consumer,"consumer can not be null");
        Call<Consumer> call = consumerConnector.update(consumer.getId(), consumer);
        return Optional.of(handleResponse(call));
    }

    public void delete(String consumerId) {
        Asserts.checkNotBlank(consumerId,"需要删除的用户id不能为空");
        consumerConnector.delete(consumerId);
    }


    @Override
    protected Logger getLogger() {
        return log;
    }
}
