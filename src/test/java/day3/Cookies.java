package day3;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Cookies {

    //@Test(priority = 1)
    void testCookies() {
        given()
                .when()
                .get("https://www.google.com/")
                .then()
                .cookie("AEC", "AVYB7cpJAUioFijp5CpnrQsoPUSZPKelHNGru1ZhJKwOatuR4dqT56OjCg")
                .log().all();


    }

    //@Test(priority = 1)
    void testGetCookies() {
        Response res = given()
                .when()
                .get("https://www.google.com/");

        String cookie_value =  res.getCookie("AEC");
        System.out.println(cookie_value);

    }

    @Test(priority = 1)
    void testMulCookies() {
        Response res = given()
                .when()
                .get("https://www.google.com/");
       String header_date =  res.getHeader("Date");
        System.out.println(header_date);

     Map<String,String> cookies_values = res.getCookies();
        for(String k: cookies_values.keySet()){
            String cookie_value = res.getCookie(k);
            System.out.println(k + ":" +cookie_value);
        }


    }
}
