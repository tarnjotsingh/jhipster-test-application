package com.reading.tvirdee.project.jhipexample.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Survey.
 */
@Entity
@Table(name = "survey")
public class Survey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "survey")
    private Set<Question> surveys = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Survey description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Question> getSurveys() {
        return surveys;
    }

    public Survey surveys(Set<Question> questions) {
        this.surveys = questions;
        return this;
    }

    public Survey addSurvey(Question question) {
        this.surveys.add(question);
        question.setSurvey(this);
        return this;
    }

    public Survey removeSurvey(Question question) {
        this.surveys.remove(question);
        question.setSurvey(null);
        return this;
    }

    public void setSurveys(Set<Question> questions) {
        this.surveys = questions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Survey survey = (Survey) o;
        if (survey.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), survey.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Survey{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
