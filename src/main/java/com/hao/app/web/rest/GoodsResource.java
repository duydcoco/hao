package com.hao.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hao.app.domain.Goods;
import com.hao.app.service.GoodsService;
import com.hao.app.web.rest.util.HeaderUtil;
import com.hao.app.web.rest.util.PaginationUtil;
import com.hao.app.web.rest.dto.GoodsDTO;
import com.hao.app.web.rest.mapper.GoodsMapper;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Goods.
 */
@RestController
@RequestMapping("/api")
public class GoodsResource {

    private final Logger log = LoggerFactory.getLogger(GoodsResource.class);
        
    @Inject
    private GoodsService goodsService;
    
    @Inject
    private GoodsMapper goodsMapper;
    
    /**
     * POST  /goods : Create a new goods.
     *
     * @param goodsDTO the goodsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new goodsDTO, or with status 400 (Bad Request) if the goods has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/goods",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GoodsDTO> createGoods(@RequestBody GoodsDTO goodsDTO) throws URISyntaxException {
        log.debug("REST request to save Goods : {}", goodsDTO);
        if (goodsDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("goods", "idexists", "A new goods cannot already have an ID")).body(null);
        }
        GoodsDTO result = goodsService.save(goodsDTO);
        return ResponseEntity.created(new URI("/api/goods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("goods", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /goods : Updates an existing goods.
     *
     * @param goodsDTO the goodsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated goodsDTO,
     * or with status 400 (Bad Request) if the goodsDTO is not valid,
     * or with status 500 (Internal Server Error) if the goodsDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/goods",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GoodsDTO> updateGoods(@RequestBody GoodsDTO goodsDTO) throws URISyntaxException {
        log.debug("REST request to update Goods : {}", goodsDTO);
        if (goodsDTO.getId() == null) {
            return createGoods(goodsDTO);
        }
        GoodsDTO result = goodsService.save(goodsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("goods", goodsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /goods : get all the goods.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of goods in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/goods",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<GoodsDTO>> getAllGoods(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Goods");
        Page<Goods> page = goodsService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/goods");
        return new ResponseEntity<>(goodsMapper.goodsToGoodsDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /goods/:id : get the "id" goods.
     *
     * @param id the id of the goodsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the goodsDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/goods/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GoodsDTO> getGoods(@PathVariable Long id) {
        log.debug("REST request to get Goods : {}", id);
        GoodsDTO goodsDTO = goodsService.findOne(id);
        return Optional.ofNullable(goodsDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /goods/:id : delete the "id" goods.
     *
     * @param id the id of the goodsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/goods/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteGoods(@PathVariable Long id) {
        log.debug("REST request to delete Goods : {}", id);
        goodsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("goods", id.toString())).build();
    }

    /**
     * SEARCH  /_search/goods?query=:query : search for the goods corresponding
     * to the query.
     *
     * @param query the query of the goods search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/goods",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<GoodsDTO>> searchGoods(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Goods for query {}", query);
        Page<Goods> page = goodsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/goods");
        return new ResponseEntity<>(goodsMapper.goodsToGoodsDTOs(page.getContent()), headers, HttpStatus.OK);
    }

}
