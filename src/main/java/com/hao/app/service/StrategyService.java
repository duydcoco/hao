package com.hao.app.service;

import com.hao.app.domain.Strategy;
import com.hao.app.web.rest.dto.StrategyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Strategy.
 */
public interface StrategyService {

    /**
     * Save a strategy.
     * 
     * @param strategyDTO the entity to save
     * @return the persisted entity
     */
    StrategyDTO save(StrategyDTO strategyDTO);

    /**
     *  Get all the strategies.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Strategy> findAll(Pageable pageable);

    /**
     *  Get the "id" strategy.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    StrategyDTO findOne(Long id);

    /**
     *  Delete the "id" strategy.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the strategy corresponding to the query.
     * 
     *  @param query the query of the search
     *  @return the list of entities
     */
    Page<Strategy> search(String query, Pageable pageable);
}
