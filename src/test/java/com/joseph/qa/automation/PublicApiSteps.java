package com.joseph.qa.automation;

import com.joseph.qa.automation.config.Config;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PublicApiSteps {

    private Response response;

    @Given("the public API endpoint is available")
    public void thePublicApiEndpointIsAvailable() {
        // Sem setup extra por enquanto
        // (poderia validar config aqui no futuro)
    }

    @When("I perform a GET request to the GitHub API")
    public void iPerformAGetRequestToGitHubApi() {

        String githubUrl = Config.get("github.url");

        response = given()
                .baseUri(githubUrl)
                .when()
                .get()
                .then()
                .extract()
                .response();
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatus) {
        assertThat(
                "Status code mismatch",
                response.statusCode(),
                is(expectedStatus)
        );
    }

    @Then("the response should contain a non-empty field {string}")
    public void theResponseShouldContainANonEmptyField(String field) {

        Object value = response.jsonPath().get(field);
        assertThat("Field not found: " + field, value, notNullValue());

        if (value instanceof String) {
            assertThat(
                    "Field is empty: " + field,
                    ((String) value).trim(),
                    not(isEmptyString())
            );
        } else if (value instanceof java.util.List) {
            assertThat(
                    "List is empty: " + field,
                    ((java.util.List<?>) value).size(),
                    greaterThan(0)
            );
        }
    }
}