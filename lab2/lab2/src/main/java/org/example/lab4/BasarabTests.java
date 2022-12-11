package org.example.lab4;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class BasarabTests {

    private static final String baseUrl = "https://f71815db-e627-4592-aa71-04eeccf13781.mock.pstmn.io";

    @BeforeClass
    public static void setup(){
        RestAssured.baseURI = baseUrl;
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.requestSpecification = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        RestAssured.responseSpecification = new ResponseSpecBuilder().build();
    }

    @Test
    public void verifyGet200(){
        given().get("/ownerName/success")
                .then()
                .body("name", equalTo("Danylo Basarab"))
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void verifyGet403(){
        given().get("/ownerName/unsuccess")
                .then()
                .body("exception", equalTo("I won't say my name!"))
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    public void verifyPost200(){
        given().post("/createSomething?permission=yes")
                .then()
                .body("result", equalTo("'Nothing' was created"))
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void verifyPost400(){
        given().post("/createSomething")
                .then()
                .body("result", equalTo("You don't have permission to create Something"))
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void verifyPut500(){
        given().put("/updateMe")
                .then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void verifyDelete410(){
        given().delete("/deleteWorld")
                .then()
                .body("world", equalTo("0"))
                .statusCode(HttpStatus.SC_GONE);
    }
}
