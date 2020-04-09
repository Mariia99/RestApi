package com.petstore.test.store;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class GetStoreInventoryTest {

    @Test
    public void getStoreInventory(){
        RestAssured.baseURI = "http://192.168.99.100:8080/api/v3/";

        Response response = RestAssured
                .given().get("com/petstore/test/store/inventory");

        System.out.println(response.getStatusCode());

        System.out.println(response.getStatusLine());
        System.out.println("---------------------------------------------------");
        System.out.println(response.getHeaders());

    }


}
