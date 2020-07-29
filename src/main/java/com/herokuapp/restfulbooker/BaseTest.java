package com.herokuapp.restfulbooker;

import org.json.JSONObject;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class BaseTest {

	
	protected Response createBooking() {

		JSONObject body = new JSONObject();

		body.put("firstname", "Jim");
		body.put("lastname", "Brown");
		body.put("totalprice", 111);
		body.put("depositpaid", false);

		JSONObject bookingdates = new JSONObject();

		bookingdates.put("checkin", "2020-07-25");
		bookingdates.put("checkout", "2020-07-29");
		body.put("bookingdates", bookingdates);
		body.put("additionalneeds", "No smoking area");

		// get response

		Response response = RestAssured.given().contentType(ContentType.JSON).body(body.toString())
				.post("https://restful-booker.herokuapp.com/booking");
		return response;
	}
	
	
}
