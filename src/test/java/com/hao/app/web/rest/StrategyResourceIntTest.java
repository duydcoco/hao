package com.hao.app.web.rest;

import com.hao.app.HaoApp;
import com.hao.app.domain.Strategy;
import com.hao.app.repository.StrategyRepository;
import com.hao.app.service.StrategyService;
import com.hao.app.repository.search.StrategySearchRepository;
import com.hao.app.web.rest.dto.StrategyDTO;
import com.hao.app.web.rest.mapper.StrategyMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the StrategyResource REST controller.
 *
 * @see StrategyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = HaoApp.class)
@WebAppConfiguration
@IntegrationTest
public class StrategyResourceIntTest {

    private static final String DEFAULT_DIV_TYPE = "AAAAA";
    private static final String UPDATED_DIV_TYPE = "BBBBB";
    private static final String DEFAULT_DIV_DATA = "AAAAA";
    private static final String UPDATED_DIV_DATA = "BBBBB";

    private static final Integer DEFAULT_MSG_CODE = 1;
    private static final Integer UPDATED_MSG_CODE = 2;
    private static final String DEFAULT_ERROR_MSG = "AAAAA";
    private static final String UPDATED_ERROR_MSG = "BBBBB";

    @Inject
    private StrategyRepository strategyRepository;

    @Inject
    private StrategyMapper strategyMapper;

    @Inject
    private StrategyService strategyService;

    @Inject
    private StrategySearchRepository strategySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restStrategyMockMvc;

    private Strategy strategy;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StrategyResource strategyResource = new StrategyResource();
        ReflectionTestUtils.setField(strategyResource, "strategyService", strategyService);
        ReflectionTestUtils.setField(strategyResource, "strategyMapper", strategyMapper);
        this.restStrategyMockMvc = MockMvcBuilders.standaloneSetup(strategyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        strategySearchRepository.deleteAll();
        strategy = new Strategy();
        strategy.setDivType(DEFAULT_DIV_TYPE);
        strategy.setDivData(DEFAULT_DIV_DATA);
        strategy.setMsgCode(DEFAULT_MSG_CODE);
        strategy.setErrorMsg(DEFAULT_ERROR_MSG);
    }

    @Test
    @Transactional
    public void createStrategy() throws Exception {
        int databaseSizeBeforeCreate = strategyRepository.findAll().size();

        // Create the Strategy
        StrategyDTO strategyDTO = strategyMapper.strategyToStrategyDTO(strategy);

        restStrategyMockMvc.perform(post("/api/strategies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(strategyDTO)))
                .andExpect(status().isCreated());

        // Validate the Strategy in the database
        List<Strategy> strategies = strategyRepository.findAll();
        assertThat(strategies).hasSize(databaseSizeBeforeCreate + 1);
        Strategy testStrategy = strategies.get(strategies.size() - 1);
        assertThat(testStrategy.getDivType()).isEqualTo(DEFAULT_DIV_TYPE);
        assertThat(testStrategy.getDivData()).isEqualTo(DEFAULT_DIV_DATA);
        assertThat(testStrategy.getMsgCode()).isEqualTo(DEFAULT_MSG_CODE);
        assertThat(testStrategy.getErrorMsg()).isEqualTo(DEFAULT_ERROR_MSG);

        // Validate the Strategy in ElasticSearch
        Strategy strategyEs = strategySearchRepository.findOne(testStrategy.getId());
        assertThat(strategyEs).isEqualToComparingFieldByField(testStrategy);
    }

    @Test
    @Transactional
    public void getAllStrategies() throws Exception {
        // Initialize the database
        strategyRepository.saveAndFlush(strategy);

        // Get all the strategies
        restStrategyMockMvc.perform(get("/api/strategies?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(strategy.getId().intValue())))
                .andExpect(jsonPath("$.[*].divType").value(hasItem(DEFAULT_DIV_TYPE.toString())))
                .andExpect(jsonPath("$.[*].divData").value(hasItem(DEFAULT_DIV_DATA.toString())))
                .andExpect(jsonPath("$.[*].msgCode").value(hasItem(DEFAULT_MSG_CODE)))
                .andExpect(jsonPath("$.[*].errorMsg").value(hasItem(DEFAULT_ERROR_MSG.toString())));
    }

    @Test
    @Transactional
    public void getStrategy() throws Exception {
        // Initialize the database
        strategyRepository.saveAndFlush(strategy);

        // Get the strategy
        restStrategyMockMvc.perform(get("/api/strategies/{id}", strategy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(strategy.getId().intValue()))
            .andExpect(jsonPath("$.divType").value(DEFAULT_DIV_TYPE.toString()))
            .andExpect(jsonPath("$.divData").value(DEFAULT_DIV_DATA.toString()))
            .andExpect(jsonPath("$.msgCode").value(DEFAULT_MSG_CODE))
            .andExpect(jsonPath("$.errorMsg").value(DEFAULT_ERROR_MSG.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStrategy() throws Exception {
        // Get the strategy
        restStrategyMockMvc.perform(get("/api/strategies/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStrategy() throws Exception {
        // Initialize the database
        strategyRepository.saveAndFlush(strategy);
        strategySearchRepository.save(strategy);
        int databaseSizeBeforeUpdate = strategyRepository.findAll().size();

        // Update the strategy
        Strategy updatedStrategy = new Strategy();
        updatedStrategy.setId(strategy.getId());
        updatedStrategy.setDivType(UPDATED_DIV_TYPE);
        updatedStrategy.setDivData(UPDATED_DIV_DATA);
        updatedStrategy.setMsgCode(UPDATED_MSG_CODE);
        updatedStrategy.setErrorMsg(UPDATED_ERROR_MSG);
        StrategyDTO strategyDTO = strategyMapper.strategyToStrategyDTO(updatedStrategy);

        restStrategyMockMvc.perform(put("/api/strategies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(strategyDTO)))
                .andExpect(status().isOk());

        // Validate the Strategy in the database
        List<Strategy> strategies = strategyRepository.findAll();
        assertThat(strategies).hasSize(databaseSizeBeforeUpdate);
        Strategy testStrategy = strategies.get(strategies.size() - 1);
        assertThat(testStrategy.getDivType()).isEqualTo(UPDATED_DIV_TYPE);
        assertThat(testStrategy.getDivData()).isEqualTo(UPDATED_DIV_DATA);
        assertThat(testStrategy.getMsgCode()).isEqualTo(UPDATED_MSG_CODE);
        assertThat(testStrategy.getErrorMsg()).isEqualTo(UPDATED_ERROR_MSG);

        // Validate the Strategy in ElasticSearch
        Strategy strategyEs = strategySearchRepository.findOne(testStrategy.getId());
        assertThat(strategyEs).isEqualToComparingFieldByField(testStrategy);
    }

    @Test
    @Transactional
    public void deleteStrategy() throws Exception {
        // Initialize the database
        strategyRepository.saveAndFlush(strategy);
        strategySearchRepository.save(strategy);
        int databaseSizeBeforeDelete = strategyRepository.findAll().size();

        // Get the strategy
        restStrategyMockMvc.perform(delete("/api/strategies/{id}", strategy.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean strategyExistsInEs = strategySearchRepository.exists(strategy.getId());
        assertThat(strategyExistsInEs).isFalse();

        // Validate the database is empty
        List<Strategy> strategies = strategyRepository.findAll();
        assertThat(strategies).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchStrategy() throws Exception {
        // Initialize the database
        strategyRepository.saveAndFlush(strategy);
        strategySearchRepository.save(strategy);

        // Search the strategy
        restStrategyMockMvc.perform(get("/api/_search/strategies?query=id:" + strategy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(strategy.getId().intValue())))
            .andExpect(jsonPath("$.[*].divType").value(hasItem(DEFAULT_DIV_TYPE.toString())))
            .andExpect(jsonPath("$.[*].divData").value(hasItem(DEFAULT_DIV_DATA.toString())))
            .andExpect(jsonPath("$.[*].msgCode").value(hasItem(DEFAULT_MSG_CODE)))
            .andExpect(jsonPath("$.[*].errorMsg").value(hasItem(DEFAULT_ERROR_MSG.toString())));
    }
}
