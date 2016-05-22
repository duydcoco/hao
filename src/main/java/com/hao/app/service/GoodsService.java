package com.hao.app.service;

import com.hao.app.domain.Goods;
import com.hao.app.web.rest.dto.GoodsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Goods.
 */
public interface GoodsService {

    /**
     * Save a goods.
     * 
     * @param goodsDTO the entity to save
     * @return the persisted entity
     */
    GoodsDTO save(GoodsDTO goodsDTO);

    /**
     *  Get all the goods.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Goods> findAll(Pageable pageable);

    /**
     *  Get the "id" goods.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    GoodsDTO findOne(Long id);

    /**
     *  Delete the "id" goods.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the goods corresponding to the query.
     * 
     *  @param query the query of the search
     *  @return the list of entities
     */
    Page<Goods> search(String query, Pageable pageable);
}
