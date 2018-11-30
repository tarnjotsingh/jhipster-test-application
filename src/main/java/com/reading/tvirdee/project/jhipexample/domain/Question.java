package com.reading.tvirdee.project.jhipexample.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Question.
 */
@Entity
@Table(name = "question")
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question")
    private String question;

    @OneToMany(mappedBy = "question")
    private Set<Choice> questions = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("surveys")
    private Survey survey;

    @OneToMany(mappedBy = "question")
    private Set<UserQuestionChoice> userQuestionChoices = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public Question question(String question) {
        this.question = question;
        return this;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Set<Choice> getQuestions() {
        return questions;
    }

    public Question questions(Set<Choice> choices) {
        this.questions = choices;
        return this;
    }

    public Question addQuestion(Choice choice) {
        this.questions.add(choice);
        choice.setQuestion(this);
        return this;
    }

    public Question removeQuestion(Choice choice) {
        this.questions.remove(choice);
        choice.setQuestion(null);
        return this;
    }

    public void setQuestions(Set<Choice> choices) {
        this.questions = choices;
    }

    public Survey getSurvey() {
        return survey;
    }

    public Question survey(Survey survey) {
        this.survey = survey;
        return this;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public Set<UserQuestionChoice> getUserQuestionChoices() {
        return userQuestionChoices;
    }

    public Question userQuestionChoices(Set<UserQuestionChoice> userQuestionChoices) {
        this.userQuestionChoices = userQuestionChoices;
        return this;
    }

    public Question addUserQuestionChoice(UserQuestionChoice userQuestionChoice) {
        this.userQuestionChoices.add(userQuestionChoice);
        userQuestionChoice.setQuestion(this);
        return this;
    }

    public Question removeUserQuestionChoice(UserQuestionChoice userQuestionChoice) {
        this.userQuestionChoices.remove(userQuestionChoice);
        userQuestionChoice.setQuestion(null);
        return this;
    }

    public void setUserQuestionChoices(Set<UserQuestionChoice> userQuestionChoices) {
        this.userQuestionChoices = userQuestionChoices;
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
        Question question = (Question) o;
        if (question.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), question.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Question{" +
            "id=" + getId() +
            ", question='" + getQuestion() + "'" +
            "}";
    }
}
