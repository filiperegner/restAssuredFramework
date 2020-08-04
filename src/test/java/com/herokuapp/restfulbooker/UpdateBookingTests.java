package com.herokuapp.restfulbooker;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UpdateBookingTests extends BaseTest {

	@Test
	public void updateBookingTest() {

		// create booking
		Response responseCreate = createBooking();

		responseCreate.prettyPrint();

		// get bookingid created
		int bookingid = responseCreate.jsonPath().getInt("bookingid");

		// create JSON body
		JSONObject body = new JSONObject();

		body.put("firstname", "Charles");
		body.put("lastname", "White");
		body.put("totalprice", 118);
		body.put("depositpaid", true);

		JSONObject bookingdates = new JSONObject();

		bookingdates.put("checkin", "2020-07-25");
		bookingdates.put("checkout", "2020-07-29");
		body.put("bookingdates", bookingdates);
		body.put("additionalneeds", "Smoking area");

		// update booking
		Response responseUpdate = RestAssured.given(spec).auth().preemptive().basic("admin", "password123")
				.contentType(ContentType.JSON).body(body.toString())
				.put("/booking/" + bookingid);

		// verifications

		// Verify response 200
		Assert.assertEquals(responseUpdate.getStatusCode(), 200, "Status code should be 200, but it's not");

		// Verify All fields
		SoftAssert softAssert = new SoftAssert();
		String actualFirstName = responseUpdate.jsonPath().getString("firstname");
		softAssert.assertEquals(actualFirstName, "Charles", "firstname in response is not expected");

		String actualLastName = responseUpdate.jsonPath().getString("lastname");
		softAssert.assertEquals(actualLastName, "White", "lastname in response is not expected");

		int price = responseUpdate.jsonPath().getInt("totalprice");
		softAssert.assertEquals(price, 118, "totalprice in response is not expected");

		boolean depositpaid = responseUpdate.jsonPath().getBoolean("depositpaid");
		softAssert.assertTrue(depositpaid, "depositpaid should be true, but it's not");

		String actualCheckin = responseUpdate.jsonPath().getString("bookingdates.checkin");
		softAssert.assertEquals(actualCheckin, "2020-07-25", "checkin in response is not expected");

		String actualCheckout = responseUpdate.jsonPath().getString("bookingdates.checkout");
		softAssert.assertEquals(actualCheckout, "2020-07-29", "checkout in response is not expected");

		String actualAdditionalneeds = responseUpdate.jsonPath().getString("additionalneeds");
		softAssert.assertEquals(actualAdditionalneeds, "Smoking area", "additionalneeds in response is not expected");

		responseUpdate.prettyPrint();

		softAssert.assertAll();

	}

}