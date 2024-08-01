package day3;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class PathQueryParams {
    @Test (priority = 1)
    void queryPath(){
        // https://reqres.in/api/users?page=2

        given()
                .pathParams("myPath", "users")
                .queryParam("page", 2)
                .when()
                .get("https://reqres.in/api/{myPath}")
                .then()
                .statusCode(200)
                .log().all();

    }
}
