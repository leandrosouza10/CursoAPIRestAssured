package br.ce.wcaquino.rest;

import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class OlaMundo{

    @Test
    public void  deveValidarBody(){
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("https://restapi.wcaquino.me/ola")
        .then()
                .log().all()
                .statusCode(200)
                .body(is("Ola Mundo!"))
                .body(containsString("Mundo"))
                .body(is(not(nullValue()))); // verifica  se o corpo não está vazio
    }
}
