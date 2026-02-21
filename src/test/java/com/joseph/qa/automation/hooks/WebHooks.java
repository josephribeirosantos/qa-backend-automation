package com.joseph.qa.automation.hooks;

import com.joseph.qa.automation.web.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class WebHooks {

    @Before("@web")
    public void beforeWebScenario() {
        boolean headless = Boolean.parseBoolean(System.getProperty("web.headless", "false"));
        DriverFactory.createDriver(headless);
    }

    @After("@web")
    public void afterWebScenario() {
        DriverFactory.quitDriver();
    }

}