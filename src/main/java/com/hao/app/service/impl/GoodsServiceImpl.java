package com.hao.app.service.impl;

import com.hao.app.service.GoodsService;
import com.hao.app.domain.Goods;
import com.hao.app.repository.GoodsRepository;
import com.hao.app.repository.search.GoodsSearchRepository;
import com.hao.app.web.rest.dto.GoodsDTO;
import com.hao.app.web.rest.mapper.GoodsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Goods.
 */
@Service
@Transactional
public class GoodsServiceImpl implements GoodsService{

    private final Logger log = LoggerFactory.getLogger(GoodsServiceImpl.class);
    
    @Inject
    private GoodsRepository goodsRepository;
    
    @Inject
    private GoodsMapper goodsMapper;
    
    @Inject
    private GoodsSearchRepository goodsSearchRepository;
    
    /**
     * Save a goods.
     * 
     * @param goodsDTO the entity to save
     * @return the persisted entity
     */
    public GoodsDTO save(GoodsDTO goodsDTO) {
        log.debug("Request to save Goods : {}", goodsDTO);
        Goods goods = goodsMapper.goodsDTOToGoods(goodsDTO);
        goods = goodsRepository.save(goods);
        GoodsDTO result = goodsMapper.goodsToGoodsDTO(goods);
        goodsSearchRepository.save(goods);
        return result;
    }

    /**
     *  Get all the goods.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Goods> findAll(Pageable pageable) {
        log.debug("Request to get all Goods");
        Page<Goods> result = goodsRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one goods by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public GoodsDTO findOne(Long id) {
        log.debug("Request to get Goods : {}", id);
        Goods goods = goodsRepository.findOne(id);
        GoodsDTO goodsDTO = goodsMapper.goodsToGoodsDTO(goods);
        return goodsDTO;
    }

    /**
     *  Delete the  goods by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Goods : {}", id);
        goodsRepository.delete(id);
        goodsSearchRepository.delete(id);
    }

    /**
     * Search for the goods corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Goods> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Goods for query {}", query);
        return goodsSearchRepository.search(queryStringQuery(query), pageable);
    }
}
