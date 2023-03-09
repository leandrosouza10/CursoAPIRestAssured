package br.ce.wcaquino.rest;

import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.request;
import static org.hamcrest.Matchers.*;


public class UserJsonTest {

    @Test
    public void  deveVerificarPrimeiroNivel(){
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("https://restapi.wcaquino.me/users/1")
        .then()
                .statusCode(200)
                .body("id",is(1))
                .body("name", containsString("Silva"))
                .body("age",greaterThan(18));
    }
    @Test
    public void deveVerificarPrimeiroNivelOutrasFormas(){
        Response response = request(Method.GET,"https://restapi.wcaquino.me/users/1");

        //path
        response.path("id");

        //jsonpath
        JsonPath jsonPath = new JsonPath(response.asString());
        Assert.assertEquals(1,jsonPath.getInt("id"));

    }
    @Test
    public void deveVerificarSegundoNivel(){

        given()
                .contentType(ContentType.JSON)
        .when()
                .get("https://restapi.wcaquino.me/users/2")
        .then()
                .statusCode(200)
                .body("name", containsString("Joaquina"))
                .body("endereco.rua",is("Rua dos bobos"));
    }

    @Test
    public void deveVerificarUmaLista(){
        given()
                .contentType(ContentType.JSON)
       .when()
                .get("https://restapi.wcaquino.me/users/3")
      .then()
                .statusCode(200)
                .body("name", containsString("Júlia"))
                .body("filhos",hasSize(2)) // Verifica o tamanho da lista
                .body("filhos[0].name",is("Zezinho"))
                .body("filhos[1].name",is("Luizinho"))
                .body("filhos.name",hasItems("Luizinho","Zezinho"))
                ;
    }
    @Test
    public void deveRetornarErro(){

        given()
                .contentType(ContentType.JSON)
       .when()
                .get("https://restapi.wcaquino.me/users/4")
       .then()
                .statusCode(404)
                .body("error",containsString("Usuário xistente"));
    }
}
