package org.example.lab3;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class BasarabTests {

    private static final String baseUrl = "https://petstore.swagger.io/v2";
    private static final String PET = "/pet",
            PET_ID = PET + "/1";

    @BeforeClass
    public static void setup(){
        RestAssured.baseURI = baseUrl;
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.requestSpecification = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        RestAssured.responseSpecification = new ResponseSpecBuilder().build();
    }

    @Test (priority = 1)
    public void verifyAddPet(){

        Map<String, ?> body = Map.of(
                "id", 1,
                "name", "Tom",
                "status", "available"

        );
        given().body(body)
                .post(PET)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test(priority = 2)
    public void verifyGetPetById(){
        given().get(PET_ID)
                .then()
                .body("name", equalTo("Tom"))
                .body("status", equalTo("available"))
                .statusCode(HttpStatus.SC_OK);
    }

    @Test(priority = 3)
    public void verifyUpdatePet(){
        Map<String, ?> body = Map.of(
                "id", 1,
                "name", "Marsel",
                "status", "pending"

        );
        given().body(body)
                .put(PET)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test(priority = 4)
    public void verifyGetPetById2(){
        given().get(PET_ID)
                .then()
                .body("name", equalTo("Marsel"))
                .body("status", equalTo("pending"))
                .statusCode(HttpStatus.SC_OK);
    }
}
