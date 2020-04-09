package com.petstore.test.user;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;

import static org.testng.AssertJUnit.assertNotNull;



public class ApiPostAndGetTest {

    private String host=System.getenv("API_URL");

    @BeforeClass
    public void setUp(){
        RestAssured.given().baseUri(host)
                .basePath("/api/v3/user/login")
                .accept(ContentType.JSON)
                .queryParam("username", "theUser")
                .queryParam("password", "12345")
                .when().get();
    }

    @AfterClass
    public void tearDown(){
        RestAssured.given().baseUri(host)
                .basePath("/api/v3/user/logout")
                .accept(ContentType.JSON)
                .when().get();
    }

    @Test(priority = 1)
    public void checkCreateUser() {

        Response response = RestAssured.given().baseUri(host)
                .basePath("/api/v3/user")
                .contentType(ContentType.JSON)
                .body(new File("data.json"))
                .when().post()
                .then()
                .extract().response();
        String statusLine =response.statusLine().substring(13,15);
        String jsonBody = response.getBody().asString();

        try {
            assertNotNull(jsonBody);
        } catch (
                Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        Assert.assertEquals(statusLine,"OK");
    }

    @Test(priority = 2)
    public void checkGetUserByUserName() {

        Response response = RestAssured.given().baseUri(host)
                .basePath("/api/v3/user/{username}")
                .accept(ContentType.JSON)
                .pathParam("username", "Jones")
                .when().get()
                .then()
                .extract().response();
        String statusLine =response.statusLine().substring(13,15);
        String jsonBody = response.getBody().asString();
        try {
            assertNotNull(jsonBody);
        } catch (
                Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        Assert.assertEquals(statusLine,"OK");
    }
}
