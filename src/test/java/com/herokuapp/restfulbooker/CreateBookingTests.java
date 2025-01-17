package com.herokuapp.restfulbooker;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateBookingTests extends BaseTest {

	@Test
	public void createBookingTest() {

		// create JSON body

		Response response = createBooking();

		response.prettyPrint();

		// verifications

		// Verify response 200
		Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200, but it's not");

		// Verify All fields
		SoftAssert softAssert = new SoftAssert();
		String actualFirstName = response.jsonPath().getString("booking.firstname");
		softAssert.assertEquals(actualFirstName, "Jim", "firstname in response is not expected");

		String actualLastName = response.jsonPath().getString("booking.lastname");
		softAssert.assertEquals(actualLastName, "Brown", "lastname in response is not expected");

		int price = response.jsonPath().getInt("booking.totalprice");
		softAssert.assertEquals(price, 111, "totalprice in response is not expected");

		boolean depositpaid = response.jsonPath().getBoolean("booking.depositpaid");
		softAssert.assertFalse(depositpaid, "depositpaid should be false, but it's not");

		String actualCheckin = response.jsonPath().getString("booking.bookingdates.checkin");
		softAssert.assertEquals(actualCheckin, "2020-07-25", "checkin in response is not expected");

		String actualCheckout = response.jsonPath().getString("booking.bookingdates.checkout");
		softAssert.assertEquals(actualCheckout, "2020-07-29", "checkout in response is not expected");

		String actualAdditionalneeds = response.jsonPath().getString("booking.additionalneeds");
		softAssert.assertEquals(actualAdditionalneeds, "No smoking area",
				"additionalneeds in response is not expected");

		softAssert.assertAll();

	}

	@Test
	public void createBookingWithPOJOTest() {

		// Create body using POJOs
		Bookingdates bookingdates = new Bookingdates("2020-07-25", "2020-07-29");
		Booking booking = new Booking("Jim", "Brown", 123, false, bookingdates, "No smoking area");

		// Get response
		Response response = RestAssured.given(spec).contentType(ContentType.JSON).body(booking).post("/booking");

		response.prettyPrint();

		Bookingid bookingid = response.as(Bookingid.class);

		// verifications

		// Verify response 200
		Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200, but it's not");

		System.out.println("Request booking: " + booking.toString());
		System.out.println("Response booking: " + bookingid.getBooking().toString());

		// Verify All fields
		Assert.assertEquals(bookingid.getBooking().toString(), booking.toString());

	}

}