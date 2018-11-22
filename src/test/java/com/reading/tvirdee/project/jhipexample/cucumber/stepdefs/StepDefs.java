package com.reading.tvirdee.project.jhipexample.cucumber.stepdefs;

import com.reading.tvirdee.project.jhipexample.JhipexampleApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = JhipexampleApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
