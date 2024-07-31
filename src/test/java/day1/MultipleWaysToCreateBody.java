package day1;

import netscape.javascript.JSObject;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class MultipleWaysToCreateBody {
    String firstName;
    int id;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

// Post Request Body using Hashmap
   // @Test(priority = 1)
    void createRequestHashMap() throws ParseException {
        Map<String, Object> data = new HashMap<>();

        // Creating bookingdates as a nested map
        Map<String, String> bookingDates = new HashMap<>();
        bookingDates.put("checkin", "2024-01-19");
        bookingDates.put("checkout", "2024-02-17");

        data.put("firstname", "Noraiz");
        data.put("lastname", "Noo");
        data.put("totalprice", 111);
        data.put("depositpaid", true);
        data.put("bookingdates", bookingDates);
        data.put("additionalneeds", "BreakFast");

        JsonPath responseData = given()
                .contentType("application/json")
                .body(data)
                .when()
                .post("https://restful-booker.herokuapp.com/booking")
                .then()
                .assertThat()
                //.defaultParser(Parser.JSON)
                .statusCode(200)
                .body("booking.firstname", equalTo("Noraiz"))
                .body("booking.lastname", equalTo("Noo"))
                .body("booking.totalprice", equalTo(111))
                .body("booking.bookingdates.checkin", equalTo("2024-01-19"))
                .body("booking.bookingdates.checkout", equalTo("2024-02-17"))
                .body("booking.additionalneeds", equalTo("BreakFast"))
                .header("Content-Type", "application/json; charset=utf-8")
                .header("Via", "1.1 vegur")
                .log().all()
                .extract()
                .jsonPath();

        firstName = responseData.getString("booking.firstname");
        id = responseData.getInt("bookingid");

        System.out.println("My name is " + firstName);
        System.out.println("Id is " + id);
    }


    // Post request body using Json
    //@Test(priority = 1)
    void postRequest(){
        JSONObject data = new JSONObject();
        data.put("firstname", "gulraiz");
        data.put("lastname", "goo");
        data.put("totalprice", 222);
        data.put("depositpaid", true);

        Map<String, String> bookingDates = new HashMap<>();
        bookingDates.put("checkin", "2024-01-19");
        bookingDates.put("checkout", "2024-02-17");

        data.put("bookingdates", bookingDates);
        data.put("additionalneeds", "BreakFast");


        given()
                .contentType("application/json")
                .body(data.toString())  // Ensure body is serialized to string
                .when()
                .post("https://restful-booker.herokuapp.com/booking")
                .then()
                .log().all();
    }

    // Post request body using POJO Class

   // @Test
    void postRequestPOJO() {
        // Create PojoClass object
        PojoClass data = new PojoClass();
        data.setFirstname("POJO");
        data.setLastname("Class");
        data.setTotalprice(15);
        data.setDepositpaid(true);

        // Create BookingDates object
        BookingDates bookingDates = new BookingDates();
        bookingDates.setCheckin("2024-01-19");
        bookingDates.setCheckout("2024-02-17");

        data.setBookingdates(bookingDates);
        data.setAdditionalneeds("Lunch");

        // Make POST request
        given()
                .contentType("application/json")
                .body(data)
                .when()
                .post("https://restful-booker.herokuapp.com/booking")
                .then()
                .log().all();
    }

    @Test
    void postRequestExternal() throws FileNotFoundException {
        // Create PojoClass object
        File f = new File("./body.json");
        FileReader fr = new FileReader(f);
        JSONTokener jt = new JSONTokener(fr);
        JSONObject data =  new JSONObject(jt);



        // Make POST request
        given()
                .contentType("application/json")
                .body(data.toString())
                .when()
                .post("https://restful-booker.herokuapp.com/booking")
                .then()
                .log().all();
    }


}

