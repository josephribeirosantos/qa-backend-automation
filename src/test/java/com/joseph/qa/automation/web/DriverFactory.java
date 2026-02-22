package com.joseph.qa.automation.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    public static void createDriver(boolean headless) {

        boolean remote = Boolean.parseBoolean(System.getProperty("web.remote", "false"));
        String remoteUrl = System.getProperty("web.remote.url", "http://localhost:4444/wd/hub");

        ChromeOptions options = new ChromeOptions();

        if (headless) {
            options.addArguments("--headless=new");
        }

        // ESSENCIAL para rodar em container
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1366,768");

        try {
            if (remote) {
                DRIVER.set(new RemoteWebDriver(new URL(remoteUrl), options));
            } else {
                WebDriverManager.chromedriver().setup();
                DRIVER.set(new ChromeDriver(options));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to create WebDriver", e);
        }
    }

    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }
}