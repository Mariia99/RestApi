package com.petstore.test.pet;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class FindByTagsTest {
    public static final String URI = "http://192.168.99.100:8080";

    @Test
    void checkBodyContainsTag() {
        RestAssured.baseURI = URI;
        List<Entity> list = new ArrayList<>();
        list = given()
                .when()
                .get("/api/v3/pet/findByTags?tags=tag1")
                .then()
                .extract().body().as(list.getClass());
        for (Object pet : list) {
            Assert.assertTrue(pet.toString().contains("tag1"));
        }
    }

    @Test
    void checkBodyContainsManyTags() {
        RestAssured.baseURI = URI;
        boolean flag = false;
        List<Entity> list = new ArrayList<>();
        list = given()
                .when()
                .get("/api/v3/pet/findByTags?tags=tag1&tags=tag2")
                .then()
                .extract().body().as(list.getClass());

        for (Object pet : list) {
            if (pet.toString().contains("name=tag1")) {
                flag = true;
            } else if (pet.toString().contains("name=tag2")) {
                flag = true;
            }
            Assert.assertTrue(flag);
        }
    }

    @Test
    void checkWithOneIncorrectTag() {
        RestAssured.baseURI = URI;

        List<Entity> list = new ArrayList<>();
        list = given()
                .when()
                .get("/api/v3/pet/findByTags?jkdljffg&tags=tag1")
                .then()
                .statusCode(200)
                .extract().body().as(list.getClass());

        for (Object pet : list) {
            Assert.assertTrue(pet.toString().contains("name=tag1"));
        }
    }

    @Test
    void checkIcorrectDataTags() {
        RestAssured.baseURI = URI;
        given()
                .expect()
                .statusCode(200)
                .when()
                .get("/api/v3/pet/findByTags?tags=4--5'2")
                .then()
                .extract().body().jsonPath().toString().contains("[]");
    }

    @Test
    void checkTooLongTagsData() {
        RestAssured.baseURI = URI;
        String randString = randomAlphabetic(100);
        given()
                .expect()
                .statusCode(200)
                .when()
                .get("/api/v3/pet/findByTags?tags=" + randString)
                .then()
                .extract().body().jsonPath().toString().contains("[]");
    }
}