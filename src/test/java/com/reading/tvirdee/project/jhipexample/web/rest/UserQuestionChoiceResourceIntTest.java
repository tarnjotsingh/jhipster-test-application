package com.reading.tvirdee.project.jhipexample.web.rest;

import com.reading.tvirdee.project.jhipexample.JhipexampleApp;

import com.reading.tvirdee.project.jhipexample.domain.UserQuestionChoice;
import com.reading.tvirdee.project.jhipexample.repository.UserQuestionChoiceRespository;
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
 * Test class for the UserQuestionChoiceResource REST controller.
 *
 * @see UserQuestionChoiceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipexampleApp.class)
public class UserQuestionChoiceResourceIntTest {

    private static final Long DEFAULT_TIME_STAMP = 1L;
    private static final Long UPDATED_TIME_STAMP = 2L;

    @Autowired
    private UserQuestionChoiceRespository userQuestionChoiceRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserQuestionChoiceMockMvc;

    private UserQuestionChoice userQuestionChoice;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserQuestionChoiceResource userQuestionChoiceResource = new UserQuestionChoiceResource(userQuestionChoiceRepository);
        this.restUserQuestionChoiceMockMvc = MockMvcBuilders.standaloneSetup(userQuestionChoiceResource)
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
    public static UserQuestionChoice createEntity(EntityManager em) {
        UserQuestionChoice userQuestionChoice = new UserQuestionChoice()
            .timeStamp(DEFAULT_TIME_STAMP);
        return userQuestionChoice;
    }

    @Before
    public void initTest() {
        userQuestionChoice = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserQuestionChoice() throws Exception {
        int databaseSizeBeforeCreate = userQuestionChoiceRepository.findAll().size();

        // Create the UserQuestionChoice
        restUserQuestionChoiceMockMvc.perform(post("/api/user-question-choices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userQuestionChoice)))
            .andExpect(status().isCreated());

        // Validate the UserQuestionChoice in the database
        List<UserQuestionChoice> userQuestionChoiceList = userQuestionChoiceRepository.findAll();
        assertThat(userQuestionChoiceList).hasSize(databaseSizeBeforeCreate + 1);
        UserQuestionChoice testUserQuestionChoice = userQuestionChoiceList.get(userQuestionChoiceList.size() - 1);
        assertThat(testUserQuestionChoice.getTimeStamp()).isEqualTo(DEFAULT_TIME_STAMP);
    }

    @Test
    @Transactional
    public void createUserQuestionChoiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userQuestionChoiceRepository.findAll().size();

        // Create the UserQuestionChoice with an existing ID
        userQuestionChoice.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserQuestionChoiceMockMvc.perform(post("/api/user-question-choices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userQuestionChoice)))
            .andExpect(status().isBadRequest());

        // Validate the UserQuestionChoice in the database
        List<UserQuestionChoice> userQuestionChoiceList = userQuestionChoiceRepository.findAll();
        assertThat(userQuestionChoiceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserQuestionChoices() throws Exception {
        // Initialize the database
        userQuestionChoiceRepository.saveAndFlush(userQuestionChoice);

        // Get all the userQuestionChoiceList
        restUserQuestionChoiceMockMvc.perform(get("/api/user-question-choices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userQuestionChoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].timeStamp").value(hasItem(DEFAULT_TIME_STAMP.intValue())));
    }
    
    @Test
    @Transactional
    public void getUserQuestionChoice() throws Exception {
        // Initialize the database
        userQuestionChoiceRepository.saveAndFlush(userQuestionChoice);

        // Get the userQuestionChoice
        restUserQuestionChoiceMockMvc.perform(get("/api/user-question-choices/{id}", userQuestionChoice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userQuestionChoice.getId().intValue()))
            .andExpect(jsonPath("$.timeStamp").value(DEFAULT_TIME_STAMP.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUserQuestionChoice() throws Exception {
        // Get the userQuestionChoice
        restUserQuestionChoiceMockMvc.perform(get("/api/user-question-choices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserQuestionChoice() throws Exception {
        // Initialize the database
        userQuestionChoiceRepository.saveAndFlush(userQuestionChoice);

        int databaseSizeBeforeUpdate = userQuestionChoiceRepository.findAll().size();

        // Update the userQuestionChoice
        UserQuestionChoice updatedUserQuestionChoice = userQuestionChoiceRepository.findById(userQuestionChoice.getId()).get();
        // Disconnect from session so that the updates on updatedUserQuestionChoice are not directly saved in db
        em.detach(updatedUserQuestionChoice);
        updatedUserQuestionChoice
            .timeStamp(UPDATED_TIME_STAMP);

        restUserQuestionChoiceMockMvc.perform(put("/api/user-question-choices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserQuestionChoice)))
            .andExpect(status().isOk());

        // Validate the UserQuestionChoice in the database
        List<UserQuestionChoice> userQuestionChoiceList = userQuestionChoiceRepository.findAll();
        assertThat(userQuestionChoiceList).hasSize(databaseSizeBeforeUpdate);
        UserQuestionChoice testUserQuestionChoice = userQuestionChoiceList.get(userQuestionChoiceList.size() - 1);
        assertThat(testUserQuestionChoice.getTimeStamp()).isEqualTo(UPDATED_TIME_STAMP);
    }

    @Test
    @Transactional
    public void updateNonExistingUserQuestionChoice() throws Exception {
        int databaseSizeBeforeUpdate = userQuestionChoiceRepository.findAll().size();

        // Create the UserQuestionChoice

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserQuestionChoiceMockMvc.perform(put("/api/user-question-choices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userQuestionChoice)))
            .andExpect(status().isBadRequest());

        // Validate the UserQuestionChoice in the database
        List<UserQuestionChoice> userQuestionChoiceList = userQuestionChoiceRepository.findAll();
        assertThat(userQuestionChoiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserQuestionChoice() throws Exception {
        // Initialize the database
        userQuestionChoiceRepository.saveAndFlush(userQuestionChoice);

        int databaseSizeBeforeDelete = userQuestionChoiceRepository.findAll().size();

        // Get the userQuestionChoice
        restUserQuestionChoiceMockMvc.perform(delete("/api/user-question-choices/{id}", userQuestionChoice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserQuestionChoice> userQuestionChoiceList = userQuestionChoiceRepository.findAll();
        assertThat(userQuestionChoiceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserQuestionChoice.class);
        UserQuestionChoice userQuestionChoice1 = new UserQuestionChoice();
        userQuestionChoice1.setId(1L);
        UserQuestionChoice userQuestionChoice2 = new UserQuestionChoice();
        userQuestionChoice2.setId(userQuestionChoice1.getId());
        assertThat(userQuestionChoice1).isEqualTo(userQuestionChoice2);
        userQuestionChoice2.setId(2L);
        assertThat(userQuestionChoice1).isNotEqualTo(userQuestionChoice2);
        userQuestionChoice1.setId(null);
        assertThat(userQuestionChoice1).isNotEqualTo(userQuestionChoice2);
    }
}
