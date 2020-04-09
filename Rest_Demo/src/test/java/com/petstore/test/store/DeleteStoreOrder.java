package com.petstore.test.store;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class DeleteStoreOrder {

    @Test
    public void getStoreInventory() {

        RestAssured.baseURI = "http://192.168.99.100:8080/api/v3/";

        Response response = RestAssured
                .given().pathParam("orderId", 1)
                .delete("com/petstore/test/store/order/{orderId}");

        System.out.println(response.getStatusCode());

        System.out.println(response.getBody().asString());
    }

}