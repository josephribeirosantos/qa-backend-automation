package com.joseph.qa.automation.stepdefinitions;

import com.joseph.qa.automation.config.Config;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetPostsSteps {

    private Response response;

    @Dado("que eu faço uma requisição GET para {string}")
    public void que_eu_faco_uma_requisicao_get_para(String path) {
        String baseUrl = Config.get("base.url");

        response =
                RestAssured.given()
                        .baseUri(baseUrl)
                        .when()
                        .get(path)
                        .then()
                        .extract()
                        .response();
    }

    @Entao("o status code deve ser {int}")
    public void o_status_code_deve_ser(Integer statusCode) {
        assertThat("Status code inválido", response.getStatusCode(), is(statusCode));
    }

    @Entao("a resposta deve conter uma lista de posts")
    public void a_resposta_deve_conter_uma_lista_de_posts() {
        assertThat(response.jsonPath().getList("$"), is(notNullValue()));
        assertThat(response.jsonPath().getList("$").size(), greaterThan(0));
        assertThat(response.jsonPath().getInt("[0].id"), greaterThan(0));
        assertThat(response.jsonPath().getString("[0].title"), not(isEmptyOrNullString()));
    }
}