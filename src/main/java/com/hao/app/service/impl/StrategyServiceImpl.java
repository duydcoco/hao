package com.hao.app.service.impl;

import com.hao.app.service.StrategyService;
import com.hao.app.domain.Strategy;
import com.hao.app.repository.StrategyRepository;
import com.hao.app.repository.search.StrategySearchRepository;
import com.hao.app.web.rest.dto.StrategyDTO;
import com.hao.app.web.rest.mapper.StrategyMapper;
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
 * Service Implementation for managing Strategy.
 */
@Service
@Transactional
public class StrategyServiceImpl implements StrategyService{

    private final Logger log = LoggerFactory.getLogger(StrategyServiceImpl.class);
    
    @Inject
    private StrategyRepository strategyRepository;
    
    @Inject
    private StrategyMapper strategyMapper;
    
    @Inject
    private StrategySearchRepository strategySearchRepository;
    
    /**
     * Save a strategy.
     * 
     * @param strategyDTO the entity to save
     * @return the persisted entity
     */
    public StrategyDTO save(StrategyDTO strategyDTO) {
        log.debug("Request to save Strategy : {}", strategyDTO);
        Strategy strategy = strategyMapper.strategyDTOToStrategy(strategyDTO);
        strategy = strategyRepository.save(strategy);
        StrategyDTO result = strategyMapper.strategyToStrategyDTO(strategy);
        strategySearchRepository.save(strategy);
        return result;
    }

    /**
     *  Get all the strategies.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Strategy> findAll(Pageable pageable) {
        log.debug("Request to get all Strategies");
        Page<Strategy> result = strategyRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one strategy by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public StrategyDTO findOne(Long id) {
        log.debug("Request to get Strategy : {}", id);
        Strategy strategy = strategyRepository.findOne(id);
        StrategyDTO strategyDTO = strategyMapper.strategyToStrategyDTO(strategy);
        return strategyDTO;
    }

    /**
     *  Delete the  strategy by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Strategy : {}", id);
        strategyRepository.delete(id);
        strategySearchRepository.delete(id);
    }

    /**
     * Search for the strategy corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Strategy> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Strategies for query {}", query);
        return strategySearchRepository.search(queryStringQuery(query), pageable);
    }
}
