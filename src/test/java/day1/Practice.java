package day1;

import org.testng.annotations.Test;
import io.restassured.path.json.JsonPath;

import java.net.http.HttpResponse;
import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Practice {
    int id;
    String name;
    String job;

   @Test (priority = 1)
    void getRequest() {
        given()

                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200)
                .body("page", equalTo(2))
                .log().all();
        System.out.println("<<<<<<<<<< GET REQUEST >>>>>>>>>>>");
    }

     @Test(priority = 2)
    void postRequest() {
        HashMap<String, String> data = new HashMap<>();

        data.put("name", "noraiz");
        data.put("job", "QA");


        JsonPath responseJson = given()
                .contentType("application/json")
                .body(data)


                .when()
                .post("https://reqres.in/api/users")

                .then()
                .statusCode(201)
                .log().all()
                .extract()// extract data
                .jsonPath(); // parse extracted data

        id = responseJson.getInt("id");
        name = responseJson.getString("name");
        job = responseJson.getString("job");

        System.out.println("<<<<<<<<<<<<<<<<<< Add Data >>>>>>>>>>>>>>>");
        System.out.println(id);
        System.out.println(name);
        System.out.println(job);
    }

    @Test (priority = 3, dependsOnMethods = "postRequest")
    void putRequest(){
        HashMap<String, String> updateData = new HashMap<>();
        updateData.put("name", "gulraiz");
        updateData.put("job", "doc");

        JsonPath responseData = given()
                .contentType("application/json")
                .body(updateData)
                .when()
                .put("https://reqres.in/api/users/"+id)
                .then()
                .statusCode(200)
                .log().all()
                .extract().jsonPath();
        name = responseData.getString("name");
        job = responseData.getString("job");


        System.out.println("<<<<<<<<<<<<<<<<<< Update Data >>>>>>>>>>>>>>>");
        System.out.println(name);
        System.out.println(job);

    }

    @Test (priority = 4)
    void deleteUser(){
       given()
               .when()
               .delete("https://reqres.in/api/users/"+id)
               .then()
               .statusCode(204);
        System.out.println("<<<<<<<<<<<<<<<<<<User deleted>>>>>>>>>>>>>");
        System.out.println("name" + name);
        System.out.println("Job" + job);
    }

}
