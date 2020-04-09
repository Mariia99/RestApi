package com.petstore.test.user;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertNotNull;

public class ApiLoginTest {

    private String host=System.getenv("API_URL");

    @Test(priority = 1)
    public void checkLoginStatusCode() {

        Response response = RestAssured.given().baseUri(host)
                .basePath("/api/v3/user/login")
                .accept(ContentType.JSON)
                .queryParam("username", "theUser")
                .queryParam("password", "12345")
                .when().get()
                .then()
                .extract().response();
        String statusLine =response.statusLine().substring(13,15);
        String jsonBody = response.getBody().asString();
            try {
            assertNotNull(jsonBody);
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        Assert.assertEquals(statusLine,"OK");
    }
}