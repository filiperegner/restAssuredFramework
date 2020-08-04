package com.herokuapp.restfulbooker;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetBookingIdsTests extends BaseTest {

	@Test
	public void getBookingIdsWithoutFiltersTest() {

		// Get response with booking ids
		Response response = RestAssured.given(spec).get("/booking");
		response.prettyPrint();

		// Verify response 200
		Assert.assertEquals(response.getStatusCode(), 200, "Status should be 200 but its not.");

		// Verify at least 1 booking id in response
		List<Integer> bookingIds = response.jsonPath().getList("bookingId");
		Assert.assertFalse(bookingIds.isEmpty(), "List of booking IDs is empty by it should not be.");

	}

	@Test
	public void getBookingIdsWithFiltersTests() {
		
		// Add query param to spec
		spec.queryParam("firstname", "Jim");
		spec.queryParam("lastname", "Jones");

		// Get response with booking ids
		Response response = RestAssured.given(spec).get("/booking");
		response.prettyPrint();

		// Verify response 200
		Assert.assertEquals(response.getStatusCode(), 200, "Status should be 200 but its not.");

		// Verify at least 1 booking id in response
		List<Integer> bookingIds = response.jsonPath().getList("bookingId");
		Assert.assertFalse(bookingIds.isEmpty(), "List of booking IDs is empty by it should not be.");

	}
}
