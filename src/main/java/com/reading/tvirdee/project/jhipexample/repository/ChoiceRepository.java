package com.reading.tvirdee.project.jhipexample.repository;

import com.reading.tvirdee.project.jhipexample.domain.Choice;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Choice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChoiceRepository extends JpaRepository<Choice, Long> {

}
