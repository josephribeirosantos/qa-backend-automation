package com.joseph.qa.automation.hooks;

import io.cucumber.java.Before;
import io.restassured.RestAssured;

public class Hooks {

    @Before
    public void beforeScenario() {
        // padrão para logs caso dê erro
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}