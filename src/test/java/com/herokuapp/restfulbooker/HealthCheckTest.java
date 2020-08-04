package com.herokuapp.restfulbooker;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

public class HealthCheckTest extends BaseTest {

	@Test
	public void healthCheckTest() {

		given().
			spec(spec).
		when().
			get("/ping").
		then().
			assertThat().
			statusCode(201);

	}
	
	@Test
	public void headersAndCookiesTest() {
		
		Header someHeader = new Header("some name","some value");
		spec.header(someHeader);
		
		Cookie someCookie = new Cookie.Builder("some cookie", "some value").build();
		spec.cookie(someCookie);

		Response response = RestAssured.given(spec).
				cookie("TestCookieName", "Test cookie value").
				header("TestHeaderName", "Test header value").
				log().all().get("/ping");
		
		// Get headers	
		Headers headers = response.getHeaders();
		System.out.println("Headers: " + headers);
		
		// Get header name and value
		Header serverHeader1 = headers.get("Server");
		System.out.println(serverHeader1.getName() + ": " + serverHeader1.getValue());
		
		// Get header value
		String serverHeader2 = response.getHeader("Server");
		System.out.println("Server: " + serverHeader2);
		
		// Get cookies
		
		Cookies cookies = response.getDetailedCookies();
		System.out.println("Cookies: " + cookies);
		

	}

}
