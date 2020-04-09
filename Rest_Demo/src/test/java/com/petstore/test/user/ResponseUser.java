package com.petstore.test.user;

import com.petstore.data.Userio;
import com.petstore.data.UserioRepository;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;
import static org.hamcrest.Matchers.equalTo;

public class ResponseUser {

    @BeforeClass
    public void setup() {
        RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://192.168.99.100/")
                .setPort(8080)
                .setBasePath("api/v3")
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        RestAssured.requestSpecification = requestSpec;
    }

    @Test(priority = 0)
    public void postCreateNewUser() {
        given()
                .spec(requestSpecification)
                .body(UserioRepository.userForPost()).
                when()
                .post("/user").
                then().statusCode(200);
    }

    @Test(priority = 1)
    public void getCreatedUser() {

        given()
                .spec(requestSpecification)
                .pathParam("username", UserioRepository.userForPost().getUsername()).
                when()
                .get("/user/{username}").
                then()
                .statusCode(200);
    }

    @Test(priority = 2)
    public void putUpdateCorrectUser() {

        given()
                .spec(requestSpecification)
                .pathParam("username", UserioRepository.userForPost().getUsername())
                .body(UserioRepository.newUserBob()).
                when()
                .put("/user/{username}").
                then()
                .statusCode(200)
                .extract()
                .body()
                .as(Userio.class);
    }

    @Test(priority = 3)
    public void getNonExistUser() {
        given()
                .spec(requestSpecification)
                .pathParam("username", UserioRepository.userForPost().getUsername()).
                when()
                .get("/user/{username}").
                then()
                .body(equalTo("User not found")).extract().body().asString();
    }

    @Test(priority = 4)
    public void getEditedUser() {
        given()
                .spec(requestSpecification)
                .pathParam("username", UserioRepository.userForPut().getUsername()).
                when()
                .get("/user/{username}").
                then()
                .statusCode(200);

    }

    @Test(priority = 5)
    public void deleteCurrentUser() {
        given()
                .spec(requestSpecification)
                .pathParam("username", UserioRepository.userForPut().getUsername()).
                when()
                .delete("/user/{username}").
                then().extract()
                .statusLine()
                .toString();


    }

    @Test(priority = 6)
    public void getDeletedUser() {
        given()
                .spec(requestSpecification)
                .pathParam("username", UserioRepository.userForPut().getUsername()).
                when()
                .get("/user/{username}").
                then()
                .statusCode(404);// for true
        //  .statusCode(200);//for false
    }
}