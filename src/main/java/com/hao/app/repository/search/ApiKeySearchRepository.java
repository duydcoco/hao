package com.hao.app.repository.search;

import com.hao.app.domain.ApiKey;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ApiKey entity.
 */
public interface ApiKeySearchRepository extends ElasticsearchRepository<ApiKey, Long> {
}
