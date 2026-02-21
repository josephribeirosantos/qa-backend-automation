package com.joseph.qa.automation.stepdefinitions;

import com.joseph.qa.automation.web.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class WebLoginSteps {

    private WebDriver driver;
    private WebDriverWait wait;

    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {
        driver = DriverFactory.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://the-internet.herokuapp.com/login");
    }

    @When("I login with username {string} and password {string}")
    public void iLoginWithUsernameAndPassword(String username, String password) {
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.cssSelector("button[type='submit']")).click();
    }

    @Then("I should see a success message")
    public void iShouldSeeASuccessMessage() {
        WebElement flash = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("flash"))
        );

        assertThat(flash.getText(), containsString("You logged into a secure area!"));
    }
}