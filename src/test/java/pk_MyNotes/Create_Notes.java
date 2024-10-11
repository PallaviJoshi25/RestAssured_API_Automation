package pk_MyNotes;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Create_Notes extends Base_Class{
	String token;
	public static String id;
	@BeforeTest
	public void login() {
			 token = Base_Class.Login("pallavijoshi2002@gmail.com","12345678");
			//System.out.println("Token is"+token);
	}
	
	@Test
	public void CreateNotes_Using_BaseClass() throws IOException, ParseException{
		JSONParser jsonparser = new JSONParser();
		// Create object for FileReader class, which help to load and read JSON file
		FileReader reader = new FileReader(".\\Test_Data\\CreateNotes.json");
		// Returning/assigning to Java Object
		Object obj = jsonparser.parse(reader);
		// Convert Java Object to JSON Object, JSONObject is typecast here
		JSONObject prodjsonobj = (JSONObject) obj;

		RestAssured.baseURI = "https://practice.expandtesting.com";
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.header("x-auth-token",token);
		System.out.println("oAuth Token is =>  " + token);
		
		request.body(prodjsonobj.toJSONString());
		
		Response response = request.request(Method.POST, "/notes/api/notes");
		response.prettyPrint();
		
		int statusCode = response.getStatusCode();
		
		Assert.assertEquals(statusCode, 200);
		
		JsonPath jsonPathEvaluator = response.getBody().jsonPath();
		id = jsonPathEvaluator.get("data.id").toString();
	}
	
	@Test(priority = 1)
	public void UpdateNotes() throws IOException, ParseException {
		JSONParser jsonparser = new JSONParser();
		// Create object for FileReader class, which help to load and read JSON file
		FileReader reader = new FileReader(".\\Test_Data\\UpdateNotes.json");
		// Returning/assigning to Java Object
		Object obj = jsonparser.parse(reader);
		// Convert Java Object to JSON Object, JSONObject is typecast here
		JSONObject prodjsonobj = (JSONObject) obj;

		RestAssured.baseURI = "https://practice.expandtesting.com";
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.header("x-auth-token",token);
		System.out.println("oAuth Token is =>  " + token);
		
		request.body(prodjsonobj.toJSONString());
		
		Response response = request.request(Method.PUT, "/notes/api/notes/"+id);
		response.prettyPrint();
		
		int statusCode = response.getStatusCode();
		
		Assert.assertEquals(statusCode, 200);
	
	}
	
	@Test(priority = 2)
	public void DeleteNote() {
		RestAssured.baseURI = "https://practice.expandtesting.com";
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.header("x-auth-token",token);
		System.out.println("oAuth Token is =>  " + token);
		
		//request.body(prodjsonobj.toJSONString());
		
		Response response = request.request(Method.DELETE, "/notes/api/notes/"+id);
		response.prettyPrint();
		
		int statusCode = response.getStatusCode();
		
		Assert.assertEquals(statusCode, 200);
	}
}
