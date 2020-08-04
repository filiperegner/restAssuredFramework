package com.herokuapp.restfulbooker;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class DeleteBookingTests extends BaseTest {

	@Test
	public void deleteBookingTest() {

		// create booking
		Response responseCreate = createBooking();

		responseCreate.prettyPrint();

		// get bookingid created
		int bookingid = responseCreate.jsonPath().getInt("bookingid");

		// delete booking

		Response responseDelete = RestAssured.given(spec).auth().preemptive().basic("admin", "password123")
				.contentType(ContentType.JSON).delete("/booking/" + bookingid);

		responseDelete.prettyPrint();

		// verifications
		
		// SoftAssert softAssert = new SoftAssert();
		// Verify response 201
		Assert.assertEquals(responseDelete.getStatusCode(), 201, "Status code should be 201, but it's not");
		
		//Assert.assertEquals(responseDelete.getBody().asString(), "Created", "Body should be Created" );
		
		Response responseGet = RestAssured.get("https://restful-booker.herokuapp.com/booking/" + bookingid);
		responseGet.prettyPrint();
		
		Assert.assertEquals(responseGet.getBody().asString(),"Not Found", "Body should be not found");

		//softAssert.assertAll();
	}

}