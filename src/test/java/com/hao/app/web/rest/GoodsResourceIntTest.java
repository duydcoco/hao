package com.hao.app.web.rest;

import com.hao.app.HaoApp;
import com.hao.app.domain.Goods;
import com.hao.app.repository.GoodsRepository;
import com.hao.app.service.GoodsService;
import com.hao.app.repository.search.GoodsSearchRepository;
import com.hao.app.web.rest.dto.GoodsDTO;
import com.hao.app.web.rest.mapper.GoodsMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the GoodsResource REST controller.
 *
 * @see GoodsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = HaoApp.class)
@WebAppConfiguration
@IntegrationTest
public class GoodsResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final Double DEFAULT_VALUE = 1D;
    private static final Double UPDATED_VALUE = 2D;

    private static final LocalDate DEFAULT_LAST_UPDATE_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_UPDATE_TIME = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private GoodsRepository goodsRepository;

    @Inject
    private GoodsMapper goodsMapper;

    @Inject
    private GoodsService goodsService;

    @Inject
    private GoodsSearchRepository goodsSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restGoodsMockMvc;

    private Goods goods;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GoodsResource goodsResource = new GoodsResource();
        ReflectionTestUtils.setField(goodsResource, "goodsService", goodsService);
        ReflectionTestUtils.setField(goodsResource, "goodsMapper", goodsMapper);
        this.restGoodsMockMvc = MockMvcBuilders.standaloneSetup(goodsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        goodsSearchRepository.deleteAll();
        goods = new Goods();
        goods.setName(DEFAULT_NAME);
        goods.setValue(DEFAULT_VALUE);
        goods.setLastUpdateTime(DEFAULT_LAST_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void createGoods() throws Exception {
        int databaseSizeBeforeCreate = goodsRepository.findAll().size();

        // Create the Goods
        GoodsDTO goodsDTO = goodsMapper.goodsToGoodsDTO(goods);

        restGoodsMockMvc.perform(post("/api/goods")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(goodsDTO)))
                .andExpect(status().isCreated());

        // Validate the Goods in the database
        List<Goods> goods = goodsRepository.findAll();
        assertThat(goods).hasSize(databaseSizeBeforeCreate + 1);
        Goods testGoods = goods.get(goods.size() - 1);
        assertThat(testGoods.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGoods.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testGoods.getLastUpdateTime()).isEqualTo(DEFAULT_LAST_UPDATE_TIME);

        // Validate the Goods in ElasticSearch
        Goods goodsEs = goodsSearchRepository.findOne(testGoods.getId());
        assertThat(goodsEs).isEqualToComparingFieldByField(testGoods);
    }

    @Test
    @Transactional
    public void getAllGoods() throws Exception {
        // Initialize the database
        goodsRepository.saveAndFlush(goods);

        // Get all the goods
        restGoodsMockMvc.perform(get("/api/goods?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(goods.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
                .andExpect(jsonPath("$.[*].lastUpdateTime").value(hasItem(DEFAULT_LAST_UPDATE_TIME.toString())));
    }

    @Test
    @Transactional
    public void getGoods() throws Exception {
        // Initialize the database
        goodsRepository.saveAndFlush(goods);

        // Get the goods
        restGoodsMockMvc.perform(get("/api/goods/{id}", goods.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(goods.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.lastUpdateTime").value(DEFAULT_LAST_UPDATE_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGoods() throws Exception {
        // Get the goods
        restGoodsMockMvc.perform(get("/api/goods/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGoods() throws Exception {
        // Initialize the database
        goodsRepository.saveAndFlush(goods);
        goodsSearchRepository.save(goods);
        int databaseSizeBeforeUpdate = goodsRepository.findAll().size();

        // Update the goods
        Goods updatedGoods = new Goods();
        updatedGoods.setId(goods.getId());
        updatedGoods.setName(UPDATED_NAME);
        updatedGoods.setValue(UPDATED_VALUE);
        updatedGoods.setLastUpdateTime(UPDATED_LAST_UPDATE_TIME);
        GoodsDTO goodsDTO = goodsMapper.goodsToGoodsDTO(updatedGoods);

        restGoodsMockMvc.perform(put("/api/goods")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(goodsDTO)))
                .andExpect(status().isOk());

        // Validate the Goods in the database
        List<Goods> goods = goodsRepository.findAll();
        assertThat(goods).hasSize(databaseSizeBeforeUpdate);
        Goods testGoods = goods.get(goods.size() - 1);
        assertThat(testGoods.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGoods.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testGoods.getLastUpdateTime()).isEqualTo(UPDATED_LAST_UPDATE_TIME);

        // Validate the Goods in ElasticSearch
        Goods goodsEs = goodsSearchRepository.findOne(testGoods.getId());
        assertThat(goodsEs).isEqualToComparingFieldByField(testGoods);
    }

    @Test
    @Transactional
    public void deleteGoods() throws Exception {
        // Initialize the database
        goodsRepository.saveAndFlush(goods);
        goodsSearchRepository.save(goods);
        int databaseSizeBeforeDelete = goodsRepository.findAll().size();

        // Get the goods
        restGoodsMockMvc.perform(delete("/api/goods/{id}", goods.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean goodsExistsInEs = goodsSearchRepository.exists(goods.getId());
        assertThat(goodsExistsInEs).isFalse();

        // Validate the database is empty
        List<Goods> goods = goodsRepository.findAll();
        assertThat(goods).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchGoods() throws Exception {
        // Initialize the database
        goodsRepository.saveAndFlush(goods);
        goodsSearchRepository.save(goods);

        // Search the goods
        restGoodsMockMvc.perform(get("/api/_search/goods?query=id:" + goods.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(goods.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].lastUpdateTime").value(hasItem(DEFAULT_LAST_UPDATE_TIME.toString())));
    }
}
