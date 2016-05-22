package com.hao.app.repository.search;

import com.hao.app.domain.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Goods entity.
 */
public interface GoodsSearchRepository extends ElasticsearchRepository<Goods, Long> {
}
