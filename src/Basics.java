import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.Payload;
import files.ReUsableMethods;



public class Basics {

	public static void main(String[] args) {
		// validate if Add Place API is working as expected
		
		//given - all input details
		// when - Submit the API - resource and method needs to be provided
		//then - validate the response
		
		RestAssured.baseURI= "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(Payload.addPlace()).when().post("maps/api/place/add/json").then().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("Server","Apache/2.4.18 (Ubuntu)").extract().response().asString();
		
		System.out.println(response);
		
		JsonPath js = new JsonPath(response);
		String placeID = js.getString("place_id");
		System.out.println(placeID);
		
		
		// Update Place
		String newAddress = "101 Gable crt., San Rafael, CA";
		
		
		given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
		.body("{\r\n" + 
				"\"place_id\":\""+placeID+"\",\r\n" + 
				"\"address\":\""+newAddress+"\",\r\n" + 
				"\"key\":\"qaclick123\"\r\n" + 
				"}").
		when().put("maps/api/place/update/json").then().assertThat().statusCode(200).body("msg",equalTo( "Address successfully updated"));
		
		
		
		// Get Place
		
		String getPlaceResponse = given().log().all().queryParams("key","qaclick123").queryParam("place_id", placeID).
		when().get("maps/api/place/get/json").then().assertThat().statusCode(200).extract().response().asString();
		
//		JsonPath js1 = new JsonPath(getPlaceResponse);
	
		JsonPath js1 = ReUsableMethods.rawToJson(getPlaceResponse);
		String  actualAddress = js1.getString("address");
		System.out.println(actualAddress);
		
		Assert.assertEquals(actualAddress, newAddress);
	}

}
