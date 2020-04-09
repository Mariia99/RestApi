package com.petstore.test.pet;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.ArrayList;
import static org.testng.Assert.assertEquals;

public class NewPetTest {
    String url = "http://192.168.99.100:8080/api/v3";

    @BeforeMethod
    void login() {
        RestAssured.baseURI = url;
        RequestSpecification httpRequest = RestAssured.given();
        RestAssured.given()
                .auth()
                .basic("annatest", "annatest");
    }

    @AfterMethod
    public void tearDown(){
        RestAssured.baseURI=url;
        Response response = RestAssured
                .given()
                .header("Content-Type","application/json")
                .delete("/pet/1");
    }

    @Test(priority = 0)
    void smokeAddPet() {
        JSONObject category = new JSONObject();
        ArrayList<String> photoUrls = new ArrayList<String>();
        ArrayList<JSONObject> tags = new ArrayList<JSONObject>();
        JSONObject pet = new JSONObject();

        category.put("id", 1);
        category.put("name", "Hamsters");

        JSONObject tag1 = new JSONObject();
        tag1.put("id", 1);
        tag1.put("name", "Syrian");
        tags.add(tag1);

        photoUrls.add("http://humster.ua/humsterphoto");

        pet.put("id", 1);
        pet.put("name", "Mickey");
        pet.put("category", category);
        pet.put("photoUrls", photoUrls);
        pet.put("tags", tags);
        pet.put("status", "available");

        RestAssured.baseURI = url;
        RequestSpecification request = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(pet.toJSONString());
        int statusCode = request
                .post("/pet")
                .getStatusCode();
        Assert.assertEquals(statusCode, 200);
    }

    @Test(priority = 1)
    void addEmptyPet() {
        JSONObject pet = new JSONObject();
        RestAssured.baseURI = url;
        RequestSpecification request = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(pet.toJSONString());
        int statusCode = request
                .post("/pet")
                .getStatusCode();
        Assert.assertEquals(statusCode, 500);
    }

    @Test(priority = 1)
    void addPetWithoutID() {
        JSONObject category = new JSONObject();
        ArrayList<String> photoUrls = new ArrayList<String>();
        ArrayList<JSONObject> tags = new ArrayList<JSONObject>();
        JSONObject pet = new JSONObject();

        category.put("id", 2);
        category.put("name", "Cats");

        JSONObject tag1 = new JSONObject();
        tag1.put("id", 3);
        tag1.put("name", "black");
        tags.add(tag1);

        photoUrls.add("http://cat.ua/catphoto");

        pet.put("name", "Murka");
        pet.put("category", category);
        pet.put("photoUrls", photoUrls);
        pet.put("tags", tags);
        pet.put("status", "sold");

        RestAssured.baseURI = url;
        RequestSpecification request = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(pet.toJSONString());
        int statusCode = request
                .post("/pet")
                .getStatusCode();
        Assert.assertEquals(statusCode, 500);
    }

    @Test(priority = 2)
    void addPetWithIDOnly() {
        JSONObject pet = new JSONObject();
        pet.put("id", 1);
        RestAssured.baseURI = url;
        RequestSpecification request = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(pet.toJSONString());
        int statusCode = request
                .post("/pet")
                .getStatusCode();
        Assert.assertEquals(statusCode, 200);
    }

    @Test(priority = 3)
    void addPetWithStringID() {
        JSONObject category = new JSONObject();
        ArrayList<String> photoUrls = new ArrayList<String>();
        ArrayList<JSONObject> tags = new ArrayList<JSONObject>();
        JSONObject pet = new JSONObject();

        category.put("id", 1);
        category.put("name", "Hamsters");

        JSONObject tag1 = new JSONObject();
        tag1.put("id", 1);
        tag1.put("name", "Syrian");
        tags.add(tag1);

        photoUrls.add("http://humster.ua/ham");

        pet.put("id", "one");
        pet.put("name", "Mickey");
        pet.put("category", category);
        pet.put("photoUrls", photoUrls);
        pet.put("tags", tags);
        pet.put("status", "available");

        RestAssured.baseURI = url;
        RequestSpecification request = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(pet.toJSONString());
        int statusCode = request
                .post("/pet")
                .getStatusCode();
        Assert.assertEquals(statusCode, 400);
    }

    @Test(priority = 4)
    void addPetWithDigitName() {
        JSONObject category = new JSONObject();
        ArrayList<String> photoUrls = new ArrayList<String>();
        ArrayList<JSONObject> tags = new ArrayList<JSONObject>();
        JSONObject pet = new JSONObject();

        category.put("id", 1);
        category.put("name", "Hamsters");

        JSONObject tag1 = new JSONObject();
        tag1.put("id", 1);
        tag1.put("name", "Syrian");
        tags.add(tag1);

        photoUrls.add("http://humster.ua/ham");

        pet.put("id", 1);
        pet.put("name", 12345);
        pet.put("category", category);
        pet.put("photoUrls", photoUrls);
        pet.put("tags", tags);
        pet.put("status", "available");

        RestAssured.baseURI = url;
        RequestSpecification request = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(pet.toJSONString());
        int statusCode = request
                .post("/pet")
                .getStatusCode();
        Assert.assertEquals(statusCode, 200);
    }

    @Test(priority = 5)
    void addPetWithoutCategoryID() {
        JSONObject category = new JSONObject();
        ArrayList<String> photoUrls = new ArrayList<String>();
        ArrayList<JSONObject> tags = new ArrayList<JSONObject>();
        JSONObject pet = new JSONObject();

        category.put("name", "Hamsters");

        JSONObject tag1 = new JSONObject();
        tag1.put("id", 1);
        tag1.put("name", "Syrian");
        tags.add(tag1);

        photoUrls.add("http://humster.ua/ham");

        pet.put("id", 1);
        pet.put("name", "Binky");
        pet.put("category", category);
        pet.put("photoUrls", photoUrls);
        pet.put("tags", tags);
        pet.put("status", "available");

        RestAssured.baseURI = url;
        RequestSpecification request = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(pet.toJSONString());
        int statusCode = request
                .post("/pet")
                .getStatusCode();
        Assert.assertEquals(statusCode, 200);
    }

    @Test(priority = 6)
    void addPetWithStringCategoryID() {
        JSONObject category = new JSONObject();
        ArrayList<String> photoUrls = new ArrayList<String>();
        ArrayList<JSONObject> tags = new ArrayList<JSONObject>();
        JSONObject pet = new JSONObject();

        category.put("id", "two");
        category.put("name", "Hamsters");

        JSONObject tag1 = new JSONObject();
        tag1.put("id", 1);
        tag1.put("name", "Syrian");
        tags.add(tag1);

        photoUrls.add("http://humster.ua/ham");

        pet.put("id", 1);
        pet.put("name", "Linda");
        pet.put("category", category);
        pet.put("photoUrls", photoUrls);
        pet.put("tags", tags);
        pet.put("status", "available");

        RestAssured.baseURI = url;
        RequestSpecification request = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(pet.toJSONString());
        int statusCode = request
                .post("/pet")
                .getStatusCode();
        Assert.assertEquals(statusCode, 400);
    }

    @Test(priority = 7)
    void addPetWithoutCategoryName() {
        JSONObject category = new JSONObject();
        ArrayList<String> photoUrls = new ArrayList<String>();
        ArrayList<JSONObject> tags = new ArrayList<JSONObject>();
        JSONObject pet = new JSONObject();

        category.put("id", 3);

        JSONObject tag1 = new JSONObject();
        tag1.put("id", 1);
        tag1.put("name", "Syrian");
        tags.add(tag1);

        photoUrls.add("http://humster.ua/ham");

        pet.put("id", 1);
        pet.put("name", "Linda");
        pet.put("category", category);
        pet.put("photoUrls", photoUrls);
        pet.put("tags", tags);
        pet.put("status", "available");

        RestAssured.baseURI = url;
        RequestSpecification request = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(pet.toJSONString());
        int statusCode = request
                .post("/pet")
                .getStatusCode();
        Assert.assertEquals(statusCode, 200);
    }

    @Test(priority = 8)
    void addPetWithDigitCategoryName() {
        JSONObject category = new JSONObject();
        ArrayList<String> photoUrls = new ArrayList<String>();
        ArrayList<JSONObject> tags = new ArrayList<JSONObject>();
        JSONObject pet = new JSONObject();

        category.put("id", 3);
        category.put("name", 1234);

        JSONObject tag1 = new JSONObject();
        tag1.put("id", 1);
        tag1.put("name", "Syrian");
        tags.add(tag1);

        photoUrls.add("http://humster.ua/ham");

        pet.put("id", 1);
        pet.put("name", "Linda");
        pet.put("category", category);
        pet.put("photoUrls", photoUrls);
        pet.put("tags", tags);
        pet.put("status", "available");

        RestAssured.baseURI = url;
        RequestSpecification request = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(pet.toJSONString());
        int statusCode = request
                .post("/pet")
                .getStatusCode();
        Assert.assertEquals(statusCode, 200);
    }

    @Test(priority = 9)
    void addPetWithManyPhotoUrls() {
        JSONObject category = new JSONObject();
        ArrayList<String> photoUrls = new ArrayList<String>();
        ArrayList<JSONObject> tags = new ArrayList<JSONObject>();
        JSONObject pet = new JSONObject();

        category.put("id", 1);
        category.put("name", "Hamsters");

        JSONObject tag1 = new JSONObject();
        tag1.put("id", 1);
        tag1.put("name", "Syrian");
        tags.add(tag1);

        photoUrls.add("http://humster.ua");
        photoUrls.add("http://cutie.ua");
        photoUrls.add("http://smallanimals.ua");

        pet.put("id", 1);
        pet.put("name", "Mickey");
        pet.put("category", category);
        pet.put("photoUrls", photoUrls);
        pet.put("tags", tags);
        pet.put("status", "available");

        RestAssured.baseURI = url;
        RequestSpecification request = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(pet.toJSONString());
        int statusCode = request
                .post("/pet")
                .getStatusCode();
        Assert.assertEquals(statusCode, 200);
    }

    @Test(priority = 10)
    void addPetWithStringTagID() {
        JSONObject category = new JSONObject();
        ArrayList<String> photoUrls = new ArrayList<String>();
        ArrayList<JSONObject> tags = new ArrayList<JSONObject>();
        JSONObject pet = new JSONObject();

        category.put("id", 1);
        category.put("name", "Hamsters");

        JSONObject tag1 = new JSONObject();
        tag1.put("id", "first");
        tag1.put("name", "Syrian");
        tags.add(tag1);

        photoUrls.add("http://humster.ua/ham");

        pet.put("id", 1);
        pet.put("name", "Mickey");
        pet.put("category", category);
        pet.put("photoUrls", photoUrls);
        pet.put("tags", tags);
        pet.put("status", "available");

        RestAssured.baseURI = url;
        RequestSpecification request = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(pet.toJSONString());
        int statusCode = request
                .post("/pet")
                .getStatusCode();
        Assert.assertEquals(statusCode, 400);
    }

    @Test(priority = 11)
    void addPetWithManyTags() {
        JSONObject category = new JSONObject();
        ArrayList<String> photoUrls = new ArrayList<String>();
        ArrayList<JSONObject> tags = new ArrayList<JSONObject>();
        JSONObject pet = new JSONObject();

        category.put("id", 1);
        category.put("name", "Hamsters");

        JSONObject tag1 = new JSONObject();
        tag1.put("id", 1);
        tag1.put("name", "Syrian");
        JSONObject tag2 = new JSONObject();
        tag2.put("id", 2);
        tag2.put("name", "Red");
        tags.add(tag1);
        tags.add(tag2);

        photoUrls.add("http://humster.ua/ham");

        pet.put("id", 1);
        pet.put("name", "Mickey");
        pet.put("category", category);
        pet.put("photoUrls", photoUrls);
        pet.put("tags", tags);
        pet.put("status", "available");

        RestAssured.baseURI = url;
        RequestSpecification request = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(pet.toJSONString());
        int statusCode = request
                .post("/pet")
                .getStatusCode();
        Assert.assertEquals(statusCode, 200);
    }

    @Test(priority = 13)
    void addPetWithNotExistedStatus() {
        JSONObject category = new JSONObject();
        ArrayList<String> photoUrls = new ArrayList<String>();
        ArrayList<JSONObject> tags = new ArrayList<JSONObject>();
        JSONObject pet = new JSONObject();

        category.put("id", 6);
        category.put("name", "Hamsters");

        JSONObject tag1 = new JSONObject();
        tag1.put("id", 1);
        tag1.put("name", "Syrian");
        tags.add(tag1);

        photoUrls.add("http://humster.ua/ham");

        pet.put("id", 1);
        pet.put("name", "Mickey");
        pet.put("category", category);
        pet.put("photoUrls", photoUrls);
        pet.put("tags", tags);
        pet.put("status", "asdf123#");

        RestAssured.baseURI = url;
        RequestSpecification request = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(pet.toJSONString());
        int statusCode = request
                .post("/pet")
                .getStatusCode();
        Assert.assertEquals(statusCode, 405);
    }
}