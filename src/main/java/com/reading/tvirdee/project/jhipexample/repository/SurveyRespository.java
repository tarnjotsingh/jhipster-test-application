package com.reading.tvirdee.project.jhipexample.repository;

import com.reading.tvirdee.project.jhipexample.domain.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRespository extends JpaRepository<Survey, Long> {
}
