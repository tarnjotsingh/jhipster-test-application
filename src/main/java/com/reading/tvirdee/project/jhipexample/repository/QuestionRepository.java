package com.reading.tvirdee.project.jhipexample.repository;

import com.reading.tvirdee.project.jhipexample.domain.Question;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Question entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

}
