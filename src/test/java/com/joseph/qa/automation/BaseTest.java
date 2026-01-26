package com.joseph.qa.automation.utils;

import io.restassured.RestAssured;

public class BaseTest {
    static {
        // URL base da API
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }
}
