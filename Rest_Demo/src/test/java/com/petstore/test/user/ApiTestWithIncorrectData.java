package com.petstore.test.user;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertNotNull;

public class ApiTestWithIncorrectData {

    private String host=System.getenv("API_URL");

    @Test
    public void checkCreateUserWithIncorrectId() {
        JSONObject content = new JSONObject();
        content.put("id", "#");
        content.put("username", "Qep");
        content.put("firstName", "Dgek");
        content.put("lastName", "Len");
        content.put("email", "lenp@email.com");
        content.put("password", "1479");
        content.put("phone", "87654");
        content.put("userStatus", "2");


        Response response = RestAssured.given().baseUri(host)
                .log().all()
                .basePath("/api/v3/user")
                .contentType(ContentType.JSON)
                .body(content)
                .when().post();
        String statusLine =response.statusLine().substring(13,24);
        String jsonBody = response.getBody().asString();
        try {
            Assert.assertNotNull(jsonBody);
        } catch (
                Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        System.out.println(jsonBody);
        Assert.assertEquals(statusLine,"Bad Request");
    }

    @Test
    public void checkGetUserByIncorrectUserName() {

        Response response = RestAssured.given().baseUri(host)
                .basePath("/api/v3/user/{username}")
                .accept(ContentType.JSON)
                .pathParam("username", "Oket")
                .when().get()
                .then()
                .extract().response();
        String jsonBody = response.getBody().asString();
        try {
            assertNotNull(jsonBody);
        } catch (
                Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        Assert.assertEquals(jsonBody,"User not found");
    }
}

