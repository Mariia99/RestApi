package com.petstore.test.pet;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class RestApiSearshTest {

    @Test
    void checkStatusCode() {
//        Specify base URL
        RestAssured.baseURI = "http://192.168.99.100:8080";
//        Request object
        RequestSpecification httpRequest = given();
//        Response object
        Response response = httpRequest.request(Method.GET, "/api/v3/pet/10");
//        Status code validation
        int statusCode = response.getStatusCode();
        System.out.println("Status code is: " + statusCode);
        assertEquals(statusCode, 200);
//        Status line verification
        String statusLine = response.getStatusLine();
        System.out.println("Status line is: " + statusLine);
        assertEquals(statusLine, "HTTP/1.1 200 OK");
    }


    @Test
    void checkPetById() {
        Response response = (Response) RestAssured.given()
                .baseUri("http://192.168.99.100:8080")
                .basePath("/api/v3/pet")
                .accept(ContentType.JSON)
                .pathParam("petId", "5")
                .when().get("/{petId}")
                .then().statusCode(200)
                .extract().response();
        JsonPath jsonpath = response.jsonPath();
        System.out.println(jsonpath.getString("name"));
        Assert.assertEquals("5", jsonpath.getString("id"));
        Assert.assertEquals("Bars", jsonpath.getString("name"));
        Assert.assertEquals("new", jsonpath.getString("status"));
    }

    @Test
    void checkPetDontExist() {
        Response response = (Response) RestAssured.given()
                .baseUri("http://192.168.99.100:8080")
                .basePath("/api/v3/pet")
                .accept(ContentType.JSON)
                .pathParam("petId", "100")
                .when().get("/{petId}")
                .then().statusCode(404)
                .extract().response();
        String jsonBody = response.getBody().asString();
        Assert.assertEquals(jsonBody, "Pet not found");
    }

    @Test
    public void shouldGetLuke() {

        Response response = RestAssured.given()
                .baseUri("http://192.168.99.100:8080")
                .basePath("/api/v3/pet")
                .accept(ContentType.JSON)
                .pathParam("petId", "5")
                .queryParam("name", "Bars")
                .queryParam("status", "new")
                .when().post("/{petId}")
                .then().statusCode(200)
                .extract().response();

    }

}
