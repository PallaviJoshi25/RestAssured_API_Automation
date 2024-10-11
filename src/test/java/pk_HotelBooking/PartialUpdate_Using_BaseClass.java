package pk_HotelBooking;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PartialUpdate_Using_BaseClass extends BaseClass{
	
	String bookingid;
	
	@BeforeTest
	public void before() {
		String token = BaseClass.createToken();
	}
	
	
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
		bookingid = jsonPathEvaluator.get("bookingid").toString();

	}
	@Test(priority = 1)
	public void partial_UpdateBooking() throws IOException, ParseException {
	JSONObject jsonobj = BaseClass.ReadFile("UpdateBooking.json");
	
	RestAssured.baseURI = "https://restful-booker.herokuapp.com";
	RequestSpecification request = RestAssured.given();

	JSONObject requestParams = new JSONObject();
	requestParams.put("firstname", "Test");
	requestParams.put("lastname", "API");
	// Add a header stating the Request body is a JSON
	request.header("Content-Type", "application/json");
	request.header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=");
	request.body(requestParams.toJSONString());
	// POST the Response
	Response response = request.request(Method.PATCH, "/booking/" + bookingid);
	// Response response = request.request(Method.POST,"/spree_oauth/token");
	response.prettyPrint();
	int statusCode = response.getStatusCode();
	// System.out.println(response.asString());
	Assert.assertEquals(statusCode, 200);
	// To get the Token from JSON Response
	JsonPath jsonPathEvaluator = response.getBody().jsonPath();
	String fname = jsonPathEvaluator.get("firstname").toString();
	System.out.println("First Name is =>  " + fname);
	Assert.assertEquals("Test", fname);

	
	}

}
