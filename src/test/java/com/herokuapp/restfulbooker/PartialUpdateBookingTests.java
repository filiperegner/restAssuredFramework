package com.herokuapp.restfulbooker;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PartialUpdateBookingTests extends BaseTest {

	@Test
	public void partialUpdateBookingTest() {

		// create booking
		Response responseCreate = createBooking();

		responseCreate.prettyPrint();

		// get bookingid created
		int bookingid = responseCreate.jsonPath().getInt("bookingid");

		// create JSON body
		JSONObject body = new JSONObject();

		body.put("firstname", "Charles");

		JSONObject bookingdates = new JSONObject();

		bookingdates.put("checkin", "2020-07-26");
		body.put("bookingdates", bookingdates);

		// partial update booking
		Response responseUpdate = RestAssured.given().auth().preemptive().basic("admin", "password123")
				.contentType(ContentType.JSON).body(body.toString())
				.patch("https://restful-booker.herokuapp.com/booking/" + bookingid);

		// verifications

		// Verify response 200
		Assert.assertEquals(responseUpdate.getStatusCode(), 200, "Status code should be 200, but it's not");

		// Verify first name and checkin updated fields
		SoftAssert softAssert = new SoftAssert();
		String actualFirstName = responseUpdate.jsonPath().getString("firstname");
		softAssert.assertEquals(actualFirstName, "Charles", "firstname in response is not expected");

		String actualCheckin = responseUpdate.jsonPath().getString("bookingdates.checkin");
		softAssert.assertEquals(actualCheckin, "2020-07-26", "checkin in response is not expected");

		responseUpdate.prettyPrint();

		softAssert.assertAll();

	}

}