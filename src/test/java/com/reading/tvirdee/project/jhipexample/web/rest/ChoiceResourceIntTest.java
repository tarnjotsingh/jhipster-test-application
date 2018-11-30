package com.reading.tvirdee.project.jhipexample.web.rest;

import com.reading.tvirdee.project.jhipexample.JhipexampleApp;

import com.reading.tvirdee.project.jhipexample.domain.Choice;
import com.reading.tvirdee.project.jhipexample.repository.ChoiceRepository;
import com.reading.tvirdee.project.jhipexample.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.reading.tvirdee.project.jhipexample.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ChoiceResource REST controller.
 *
 * @see ChoiceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipexampleApp.class)
public class ChoiceResourceIntTest {

    private static final String DEFAULT_CHOICE = "AAAAAAAAAA";
    private static final String UPDATED_CHOICE = "BBBBBBBBBB";

    private static final Integer DEFAULT_WEIGHT = 1;
    private static final Integer UPDATED_WEIGHT = 2;

    @Autowired
    private ChoiceRepository choiceRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restChoiceMockMvc;

    private Choice choice;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChoiceResource choiceResource = new ChoiceResource(choiceRepository);
        this.restChoiceMockMvc = MockMvcBuilders.standaloneSetup(choiceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Choice createEntity(EntityManager em) {
        Choice choice = new Choice()
            .choice(DEFAULT_CHOICE)
            .weight(DEFAULT_WEIGHT);
        return choice;
    }

    @Before
    public void initTest() {
        choice = createEntity(em);
    }

    @Test
    @Transactional
    public void createChoice() throws Exception {
        int databaseSizeBeforeCreate = choiceRepository.findAll().size();

        // Create the Choice
        restChoiceMockMvc.perform(post("/api/choices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(choice)))
            .andExpect(status().isCreated());

        // Validate the Choice in the database
        List<Choice> choiceList = choiceRepository.findAll();
        assertThat(choiceList).hasSize(databaseSizeBeforeCreate + 1);
        Choice testChoice = choiceList.get(choiceList.size() - 1);
        assertThat(testChoice.getChoice()).isEqualTo(DEFAULT_CHOICE);
        assertThat(testChoice.getWeight()).isEqualTo(DEFAULT_WEIGHT);
    }

    @Test
    @Transactional
    public void createChoiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = choiceRepository.findAll().size();

        // Create the Choice with an existing ID
        choice.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChoiceMockMvc.perform(post("/api/choices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(choice)))
            .andExpect(status().isBadRequest());

        // Validate the Choice in the database
        List<Choice> choiceList = choiceRepository.findAll();
        assertThat(choiceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllChoices() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);

        // Get all the choiceList
        restChoiceMockMvc.perform(get("/api/choices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(choice.getId().intValue())))
            .andExpect(jsonPath("$.[*].choice").value(hasItem(DEFAULT_CHOICE.toString())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)));
    }
    
    @Test
    @Transactional
    public void getChoice() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);

        // Get the choice
        restChoiceMockMvc.perform(get("/api/choices/{id}", choice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(choice.getId().intValue()))
            .andExpect(jsonPath("$.choice").value(DEFAULT_CHOICE.toString()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT));
    }

    @Test
    @Transactional
    public void getNonExistingChoice() throws Exception {
        // Get the choice
        restChoiceMockMvc.perform(get("/api/choices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChoice() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);

        int databaseSizeBeforeUpdate = choiceRepository.findAll().size();

        // Update the choice
        Choice updatedChoice = choiceRepository.findById(choice.getId()).get();
        // Disconnect from session so that the updates on updatedChoice are not directly saved in db
        em.detach(updatedChoice);
        updatedChoice
            .choice(UPDATED_CHOICE)
            .weight(UPDATED_WEIGHT);

        restChoiceMockMvc.perform(put("/api/choices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedChoice)))
            .andExpect(status().isOk());

        // Validate the Choice in the database
        List<Choice> choiceList = choiceRepository.findAll();
        assertThat(choiceList).hasSize(databaseSizeBeforeUpdate);
        Choice testChoice = choiceList.get(choiceList.size() - 1);
        assertThat(testChoice.getChoice()).isEqualTo(UPDATED_CHOICE);
        assertThat(testChoice.getWeight()).isEqualTo(UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void updateNonExistingChoice() throws Exception {
        int databaseSizeBeforeUpdate = choiceRepository.findAll().size();

        // Create the Choice

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChoiceMockMvc.perform(put("/api/choices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(choice)))
            .andExpect(status().isBadRequest());

        // Validate the Choice in the database
        List<Choice> choiceList = choiceRepository.findAll();
        assertThat(choiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChoice() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);

        int databaseSizeBeforeDelete = choiceRepository.findAll().size();

        // Get the choice
        restChoiceMockMvc.perform(delete("/api/choices/{id}", choice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Choice> choiceList = choiceRepository.findAll();
        assertThat(choiceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Choice.class);
        Choice choice1 = new Choice();
        choice1.setId(1L);
        Choice choice2 = new Choice();
        choice2.setId(choice1.getId());
        assertThat(choice1).isEqualTo(choice2);
        choice2.setId(2L);
        assertThat(choice1).isNotEqualTo(choice2);
        choice1.setId(null);
        assertThat(choice1).isNotEqualTo(choice2);
    }
}
