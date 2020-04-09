package com.petstore.test.pet;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class FindByStatusTest {
    public static final String URI = "http://192.168.99.100:8080";

    @Test
    void testContentType() {
        RestAssured.baseURI = URI;
        RequestSpecification httpRequest = given();
        Response response = httpRequest.request(Method.GET, "/api/v3/pet/findByStatus?status=available");
        String responseBody = response.getBody().asString();
        String contentType = response.header("Content-Type");
        Assert.assertEquals(contentType, "application/json");
    }

    @Test
    void checkAvailableStatus() {
        RestAssured.baseURI = URI;
        RequestSpecification httpRequest = given();
        Response response = httpRequest.request(Method.GET, "/api/v3/pet/findByStatus?status=available");
        List<LinkedHashMap> jsonResponse = response.jsonPath().getList("$");
        for (int i = 0; i < jsonResponse.size(); i++) {
            Assert.assertEquals(jsonResponse.get(i).get("status"), "available");
        }
    }

    @Test
    void checkPendingStatus() {
        RestAssured.baseURI = URI;
        RequestSpecification httpRequest = given();
        Response response = httpRequest.request(Method.GET, "/api/v3/pet/findByStatus?status=pending");
        List<LinkedHashMap> jsonResponse = response.jsonPath().getList("$");
        for (int i = 0; i < jsonResponse.size(); i++) {
            Assert.assertEquals(jsonResponse.get(i).get("status"), "pending");
        }
    }

    @Test
    void checkSoldStatus() {
        RestAssured.baseURI = URI;
        RequestSpecification httpRequest = given();
        Response response = httpRequest.request(Method.GET, "/api/v3/pet/findByStatus?status=sold");
        List<LinkedHashMap> jsonResponse = response.jsonPath().getList("$");
        for (int i = 0; i < jsonResponse.size(); i++) {
            Assert.assertEquals(jsonResponse.get(i).get("status"), "sold");
        }
    }

    @Test
    void checkEmptyStatus() {
        RestAssured.baseURI = URI;
        RequestSpecification httpRequest = given();
        Response response = httpRequest.request(Method.GET, "/api/v3/pet/findByStatus?status=");

        Assert.assertEquals(response.getStatusCode(), 400);
        JsonPath jsonResponse = response.jsonPath();
        Assert.assertTrue(jsonResponse.getString("message").contains("status value `` is not in the allowable values"));
    }

    @Test
    void checkIncorrectDataStatus() {
        String STATUSVALUE = "dfdgdhj";
        RestAssured.baseURI = URI;
        RequestSpecification httpRequest = given();
        Response response = httpRequest.request(Method.GET, "/api/v3/pet/findByStatus?status=" + STATUSVALUE);
        Assert.assertEquals(response.getStatusCode(), 400);

        JsonPath jsonResponse = response.jsonPath();
        Assert.assertTrue(jsonResponse.getString("message").contains("status value `" + STATUSVALUE +
                "` is not in the allowable values"));
    }

    @Test
    void checkTooLongStatusdata() {
        String randString = randomAlphabetic(100);
        RestAssured.baseURI = URI;
        RequestSpecification httpRequest = given();
        Response response = httpRequest.request(Method.GET, "/api/v3/pet/findByStatus?status=" + randString);
        Assert.assertEquals(response.getStatusCode(), 400);

        JsonPath jsonResponse = response.jsonPath();
        Assert.assertTrue(jsonResponse.getString("message").contains("status value `" + randString +
                "` is not in the allowable values"));
    }
}
