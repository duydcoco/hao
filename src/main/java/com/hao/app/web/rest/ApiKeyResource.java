package com.hao.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hao.app.domain.ApiKey;
import com.hao.app.service.ApiKeyService;
import com.hao.app.web.rest.util.HeaderUtil;
import com.hao.app.web.rest.util.PaginationUtil;
import com.hao.app.web.rest.dto.ApiKeyDTO;
import com.hao.app.web.rest.mapper.ApiKeyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ApiKey.
 */
@RestController
@RequestMapping("/api")
public class ApiKeyResource {

    private final Logger log = LoggerFactory.getLogger(ApiKeyResource.class);
        
    @Inject
    private ApiKeyService apiKeyService;
    
    @Inject
    private ApiKeyMapper apiKeyMapper;
    
    /**
     * POST  /api-keys : Create a new apiKey.
     *
     * @param apiKeyDTO the apiKeyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new apiKeyDTO, or with status 400 (Bad Request) if the apiKey has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/api-keys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ApiKeyDTO> createApiKey(@Valid @RequestBody ApiKeyDTO apiKeyDTO) throws URISyntaxException {
        log.debug("REST request to save ApiKey : {}", apiKeyDTO);
        if (apiKeyDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("apiKey", "idexists", "A new apiKey cannot already have an ID")).body(null);
        }
        ApiKeyDTO result = apiKeyService.save(apiKeyDTO);
        return ResponseEntity.created(new URI("/api/api-keys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("apiKey", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /api-keys : Updates an existing apiKey.
     *
     * @param apiKeyDTO the apiKeyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated apiKeyDTO,
     * or with status 400 (Bad Request) if the apiKeyDTO is not valid,
     * or with status 500 (Internal Server Error) if the apiKeyDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/api-keys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ApiKeyDTO> updateApiKey(@Valid @RequestBody ApiKeyDTO apiKeyDTO) throws URISyntaxException {
        log.debug("REST request to update ApiKey : {}", apiKeyDTO);
        if (apiKeyDTO.getId() == null) {
            return createApiKey(apiKeyDTO);
        }
        ApiKeyDTO result = apiKeyService.save(apiKeyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("apiKey", apiKeyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /api-keys : get all the apiKeys.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of apiKeys in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/api-keys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<ApiKeyDTO>> getAllApiKeys(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ApiKeys");
        Page<ApiKey> page = apiKeyService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/api-keys");
        return new ResponseEntity<>(apiKeyMapper.apiKeysToApiKeyDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /api-keys/:id : get the "id" apiKey.
     *
     * @param id the id of the apiKeyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the apiKeyDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/api-keys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ApiKeyDTO> getApiKey(@PathVariable Long id) {
        log.debug("REST request to get ApiKey : {}", id);
        ApiKeyDTO apiKeyDTO = apiKeyService.findOne(id);
        return Optional.ofNullable(apiKeyDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /api-keys/:id : delete the "id" apiKey.
     *
     * @param id the id of the apiKeyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/api-keys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteApiKey(@PathVariable Long id) {
        log.debug("REST request to delete ApiKey : {}", id);
        apiKeyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("apiKey", id.toString())).build();
    }

    /**
     * SEARCH  /_search/api-keys?query=:query : search for the apiKey corresponding
     * to the query.
     *
     * @param query the query of the apiKey search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/api-keys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<ApiKeyDTO>> searchApiKeys(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of ApiKeys for query {}", query);
        Page<ApiKey> page = apiKeyService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/api-keys");
        return new ResponseEntity<>(apiKeyMapper.apiKeysToApiKeyDTOs(page.getContent()), headers, HttpStatus.OK);
    }

}
