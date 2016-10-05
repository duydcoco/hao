package com.hao.app.service.impl;

import com.hao.app.service.ApiKeyService;
import com.hao.app.domain.ApiKey;
import com.hao.app.repository.ApiKeyRepository;
import com.hao.app.repository.search.ApiKeySearchRepository;
import com.hao.app.web.rest.dto.ApiKeyDTO;
import com.hao.app.web.rest.mapper.ApiKeyMapper;
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
 * Service Implementation for managing ApiKey.
 */
@Service
@Transactional
public class ApiKeyServiceImpl implements ApiKeyService{

    private final Logger log = LoggerFactory.getLogger(ApiKeyServiceImpl.class);
    
    @Inject
    private ApiKeyRepository apiKeyRepository;
    
    @Inject
    private ApiKeyMapper apiKeyMapper;
    
    @Inject
    private ApiKeySearchRepository apiKeySearchRepository;
    
    /**
     * Save a apiKey.
     * 
     * @param apiKeyDTO the entity to save
     * @return the persisted entity
     */
    public ApiKeyDTO save(ApiKeyDTO apiKeyDTO) {
        log.debug("Request to save ApiKey : {}", apiKeyDTO);
        ApiKey apiKey = apiKeyMapper.apiKeyDTOToApiKey(apiKeyDTO);
        apiKey = apiKeyRepository.save(apiKey);
        ApiKeyDTO result = apiKeyMapper.apiKeyToApiKeyDTO(apiKey);
        apiKeySearchRepository.save(apiKey);
        return result;
    }

    /**
     *  Get all the apiKeys.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<ApiKey> findAll(Pageable pageable) {
        log.debug("Request to get all ApiKeys");
        Page<ApiKey> result = apiKeyRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one apiKey by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ApiKeyDTO findOne(Long id) {
        log.debug("Request to get ApiKey : {}", id);
        ApiKey apiKey = apiKeyRepository.findOne(id);
        ApiKeyDTO apiKeyDTO = apiKeyMapper.apiKeyToApiKeyDTO(apiKey);
        return apiKeyDTO;
    }

    /**
     *  Delete the  apiKey by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ApiKey : {}", id);
        apiKeyRepository.delete(id);
        apiKeySearchRepository.delete(id);
    }

    /**
     * Search for the apiKey corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ApiKey> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ApiKeys for query {}", query);
        return apiKeySearchRepository.search(queryStringQuery(query), pageable);
    }
}
