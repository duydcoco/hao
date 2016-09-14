package com.hao.app.repository.search;

import com.hao.app.domain.Strategy;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Strategy entity.
 */
public interface StrategySearchRepository extends ElasticsearchRepository<Strategy, Long> {
}
