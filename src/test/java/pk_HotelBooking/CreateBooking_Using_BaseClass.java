package pk_HotelBooking;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CreateBooking_Using_BaseClass extends BaseClass{
	@Test
	public void createBooking() throws IOException, ParseException {

		JSONObject jsonobj = BaseClass.ReadFile("CreateBooking2.json");
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(jsonobj.toJSONString());
		// POST the Response
		Response response = request.request(Method.POST, "/booking");
		// Response response = request.request(Method.POST,"/spree_oauth/token");
		response.prettyPrint();
		int statusCode = response.getStatusCode();
		// System.out.println(response.asString());
		Assert.assertEquals(statusCode, 200);
		// To get the Token from JSON Response
		JsonPath jsonPathEvaluator = response.getBody().jsonPath();
		String fname = jsonPathEvaluator.get("booking.firstname").toString();
		System.out.println("First Name is =>  " + fname);
		Assert.assertEquals("Abhi", fname);
		

	}
}
