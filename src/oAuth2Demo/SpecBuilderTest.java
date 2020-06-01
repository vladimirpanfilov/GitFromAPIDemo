package oAuth2Demo;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class SpecBuilderTest {

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
	 
	 ResponseSpecification resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
	 
	RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key","qaclick123")
	 .setContentType(ContentType.JSON).build();
	 
	 RequestSpecification  res = given().spec(req)
		.body(p);
	Response response= res.when().post("/maps/api/place/add/json")
		.then().spec(resspec).extract().response();

	String responseString = response.asString();
	System.out.println(responseString);
	}

}
