package com.reading.tvirdee.project.jhipexample.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.reading.tvirdee.project.jhipexample.domain.UserQuestionChoice;
import com.reading.tvirdee.project.jhipexample.repository.UserQuestionChoiceRespository;
import com.reading.tvirdee.project.jhipexample.web.rest.errors.BadRequestAlertException;
import com.reading.tvirdee.project.jhipexample.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UserQuestionChoice.
 */
@RestController
@RequestMapping("/api")
public class UserQuestionChoiceResource {

    private final Logger log = LoggerFactory.getLogger(UserQuestionChoiceResource.class);

    private static final String ENTITY_NAME = "userQuestionChoice";

    private final UserQuestionChoiceRespository userQuestionChoiceRespository;

    public UserQuestionChoiceResource(UserQuestionChoiceRespository userQuestionChoiceRespository) {
        this.userQuestionChoiceRespository = userQuestionChoiceRespository;
    }

    /**
     * POST  /user-question-choices : Create a new userQuestionChoice.
     *
     * @param userQuestionChoice the userQuestionChoice to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userQuestionChoice, or with status 400 (Bad Request) if the userQuestionChoice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-question-choices")
    @Timed
    public ResponseEntity<UserQuestionChoice> createUserQuestionChoice(@RequestBody UserQuestionChoice userQuestionChoice) throws URISyntaxException {
        log.debug("REST request to save UserQuestionChoice : {}", userQuestionChoice);
        if (userQuestionChoice.getId() != null) {
            throw new BadRequestAlertException("A new userQuestionChoice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserQuestionChoice result = userQuestionChoiceRespository.save(userQuestionChoice);
        return ResponseEntity.created(new URI("/api/user-question-choices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-question-choices : Updates an existing userQuestionChoice.
     *
     * @param userQuestionChoice the userQuestionChoice to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userQuestionChoice,
     * or with status 400 (Bad Request) if the userQuestionChoice is not valid,
     * or with status 500 (Internal Server Error) if the userQuestionChoice couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-question-choices")
    @Timed
    public ResponseEntity<UserQuestionChoice> updateUserQuestionChoice(@RequestBody UserQuestionChoice userQuestionChoice) throws URISyntaxException {
        log.debug("REST request to update UserQuestionChoice : {}", userQuestionChoice);
        if (userQuestionChoice.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserQuestionChoice result = userQuestionChoiceRespository.save(userQuestionChoice);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userQuestionChoice.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-question-choices : get all the userQuestionChoices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userQuestionChoices in body
     */
    @GetMapping("/user-question-choices")
    @Timed
    public List<UserQuestionChoice> getAllUserQuestionChoices() {
        log.debug("REST request to get all UserQuestionChoices");
        return userQuestionChoiceRespository.findAll();
    }

    /**
     * GET  /user-question-choices/:id : get the "id" userQuestionChoice.
     *
     * @param id the id of the userQuestionChoice to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userQuestionChoice, or with status 404 (Not Found)
     */
    @GetMapping("/user-question-choices/{id}")
    @Timed
    public ResponseEntity<UserQuestionChoice> getUserQuestionChoice(@PathVariable Long id) {
        log.debug("REST request to get UserQuestionChoice : {}", id);
        Optional<UserQuestionChoice> userQuestionChoice = userQuestionChoiceRespository.findById(id);
        return ResponseUtil.wrapOrNotFound(userQuestionChoice);
    }

    /**
     * DELETE  /user-question-choices/:id : delete the "id" userQuestionChoice.
     *
     * @param id the id of the userQuestionChoice to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-question-choices/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserQuestionChoice(@PathVariable Long id) {
        log.debug("REST request to delete UserQuestionChoice : {}", id);

        userQuestionChoiceRespository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
