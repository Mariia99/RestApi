package com.petstore.test.user;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ApiLogoutTest {

    private String host=System.getenv("API_URL");

    @Test(priority = 1)
    public void checkLogout() {

        Response response = RestAssured.given().baseUri(host)
                .basePath("/api/v3/user/logout")
                .accept(ContentType.JSON)
                .when().get()
                .then()
                .extract().response();
        String statusLine =response.statusLine().substring(13,15);
        String jsonBody = response.getBody().asString();
        try {
            Assert.assertNotNull(jsonBody);
        } catch (
                Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        Assert.assertEquals(statusLine,"OK");
    }
}
