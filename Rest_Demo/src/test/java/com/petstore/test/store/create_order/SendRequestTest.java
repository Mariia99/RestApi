package com.petstore.test.store.create_order;

import com.petstore.data.Order;
import com.petstore.data.OrderRepository;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;
import static org.hamcrest.Matchers.equalTo;

public class SendRequestTest {

    @BeforeClass
    public void setRequestSpec() {
        RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://192.168.99.100/")
                .setPort(8080)
                .setBasePath("api/v3/store/order")
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
        RestAssured.requestSpecification = requestSpec;
    }

    @Test
    public void sendRequestBody_ValidOrder() {
        Order order = given()
                .spec(requestSpecification)
                .body(OrderRepository.validOrder()).
        when()
                .post().
        then()
                .extract()
                .body()
                .as(Order.class);
    }

    @Test
    public void sendRequestBody_AllNull() {
        given()
                .spec(requestSpecification)
                .body(OrderRepository.invalidOrder_AllNull()).
        when()
                .post().
        then()
                .body("id", equalTo(0))
                .body("petId", equalTo(0))
                .body("quantity", equalTo(0))
                .body("complete", equalTo(false));
    }

    @Test
    public void sendRequestBodyWithId_NULL() {
        given()
                .spec(requestSpecification)
                .body(" \"id\": \"null\" ").
        when()
                .post().
        then()
                .statusCode(400)
                .body("code", equalTo(400))
                .body("message", Matchers.containsStringIgnoringCase("input error"));
    }

    @Test
    public void sendRequestBodyWithId_String() {
        given()
                .spec(requestSpecification)
                .body(" \"id\": \"some string value\" ").
        when()
                .post().
        then()
                .statusCode(400)
                .body("code", equalTo(400))
                .body("message", Matchers.containsStringIgnoringCase("input error"));
    }
}
