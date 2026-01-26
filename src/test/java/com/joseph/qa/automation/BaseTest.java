package com.joseph.qa.automation.utils;

import io.restassured.RestAssured;

public class BaseTest {

    static {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }
}