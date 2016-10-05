package com.hao.app.service;

import com.hao.app.domain.ApiKey;
import com.hao.app.web.rest.dto.ApiKeyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing ApiKey.
 */
public interface ApiKeyService {

    /**
     * Save a apiKey.
     * 
     * @param apiKeyDTO the entity to save
     * @return the persisted entity
     */
    ApiKeyDTO save(ApiKeyDTO apiKeyDTO);

    /**
     *  Get all the apiKeys.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ApiKey> findAll(Pageable pageable);

    /**
     *  Get the "id" apiKey.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    ApiKeyDTO findOne(Long id);

    /**
     *  Delete the "id" apiKey.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the apiKey corresponding to the query.
     * 
     *  @param query the query of the search
     *  @return the list of entities
     */
    Page<ApiKey> search(String query, Pageable pageable);
}
