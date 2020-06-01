package oAuth2Demo;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.AddPlace;
import pojo.Location;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class serializeTest {

	public static void main(String[] args) {
	 RestAssured.baseURI="https://rahulshettyacademy.com";
	 
	 AddPlace p = new AddPlace();
	 p.setAccuracy(50);
	 p.setAddress("70 Summer walk, USA");
	 p.setPhone_number("(+91) 983 893 3930");
	 p.setWebsite("https://rahulshettyacademy.com");
	 p.setLanguage("French-IN");
	 p.setName("Frontline house new test");
	 
	 List<String> myList = new ArrayList<String>();
	 myList.add("shoe park");
	 myList.add("shop");
	 
	 p.setTypes(myList);
	 
	 Location l = new Location();
	 l.setlat(-38.383494);
	 l.setlng(33.427362);
	 
	 p.setLocation(l);
	 
	 
	//private Location location;
	//private String types;
	 
	 
	 
	 Response  res = given().log().all().queryParam("key", "qaclick123")
		.body(p)
		.when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).extract().response();

	String responseString = res.asString();
	System.out.println(responseString);
	}

}
