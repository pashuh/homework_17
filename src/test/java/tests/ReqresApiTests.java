package tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;

public class ReqresApiTests {

    @BeforeAll
    public static void beforAll() {
        RestAssured.baseURI = "https://reqres.in/";
    }

    @Test
    public void singleUser() {
        given()
                .log().uri()
                .log().body()
                .when()
                .get("/api/users?page=2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("total_pages", is(2));
    }


    @Test
    public void singleResource() {
        given()
                .log().body()
                .when()
                .get("/api/unknown/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("support", hasKey("text"));
    }

    @Test
    public void createTest() {
        given()
                .log().body()
                .contentType(JSON)
                .body("{ \"name\": \"test333\", \"job\": \"QAEngineer\" }")
                .when()
                .post("/api/users")
                .then()
                .log().body()
                .statusCode(201)
                .body("job", is("QAEngineer"));
    }

    @Test
    public void updateJobTest() {
        given()
                .log().body()
                .contentType(JSON)
                .body("{ \"name\": \"test999\", \"job\": \"QA\" }")
                .when()
                .patch("/api/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("job", is("QA"));
    }

    @Test
    public void registerUnsuccessful() {
        given()
                .log().body()
                .contentType(JSON)
                .body("{ \"email\": \"sydney@fife\" }")
                .when()
                .post("/api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }
}
