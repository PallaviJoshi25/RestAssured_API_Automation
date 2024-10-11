package pk_HotelBooking;

import java.io.FileNotFoundException;
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

public class BaseClass {
public static String outh_token;
	public static String createToken() {

		RestAssured.baseURI = "https://restful-booker.herokuapp.com";
		RequestSpecification request = RestAssured.given();

		JSONObject requestParams = new JSONObject();
		requestParams.put("username", "admin");
		requestParams.put("password", "password123");
		// Add a header stating the Request body is a JSON
		request.header("Content-Type", "application/json");
		request.header("Authorization","Basic YWRtaW46cGFzc3dvcmQxMjM=");
		request.body(requestParams.toJSONString());
		// POST the Response
		Response response = request.request(Method.POST, "/auth");
		// Response response = request.request(Method.POST,"/spree_oauth/token");
		response.prettyPrint();
		int statusCode = response.getStatusCode();
		// System.out.println(response.asString());
		Assert.assertEquals(statusCode, 200);
		// To get the Token from JSON Response
		JsonPath jsonPathEvaluator = response.getBody().jsonPath();
		String outh_token = jsonPathEvaluator.get("token").toString();
		System.out.println("oAuth Token is =>  " + outh_token);
		return outh_token;
	}

	public static JSONObject ReadFile(String filename) throws IOException, ParseException {
		// Create json object of JSONParser class to parse the JSON data
		JSONParser jsonparser = new JSONParser();
		// Create object for FileReader class, which help to load and read JSON file
		
		//FileReader reader = new FileReader(".\\Test_Data\\CreateBooking2.json");
		FileReader reader = new FileReader(".\\Test_Data\\"+filename);
		
		// Returning/assigning to Java Object
		Object obj = jsonparser.parse(reader);
		// Convert Java Object to JSON Object, JSONObject is typecast here
		JSONObject prodjsonobj = (JSONObject) obj;
		return prodjsonobj;
	}
}
