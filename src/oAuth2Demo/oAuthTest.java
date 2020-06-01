package oAuth2Demo;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;

public class oAuthTest {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		
//		System.setProperty("webdriver.chrome.driver", "C:/Users/vladi/eclipse/java-2019-12/eclipse/chromedriver.exe");
//		WebDriver driver = new ChromeDriver();
//		driver.get("https://accounts.google.com/signin/oauth/identifier?scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&auth_url=https%3A%2F%2Faccounts.google.com%2Fo%2Foauth2%2Fv2%2Fauth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https%3A%2F%2Frahulshettyacademy.com%2FgetCourse.php&o2v=2&as=hCF9O5cIlfMMOML6QASdNQ&flowName=GeneralOAuthFlow");
//		WebElement emailField = driver.findElement(By.xpath(".//input[@id='identifierId']"));
//		emailField.sendKeys("srinath19830");
//		emailField.sendKeys(Keys.ENTER);
//		WebElement passwordFiled = driver.findElement(By.cssSelector("input[type='password']"));
//		passwordFiled.sendKeys("password");
//		passwordFiled.sendKeys(Keys.ENTER);
//		
//		Thread.sleep(3000);
//		String url = driver.getCurrentUrl();
		
		String url ="https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2FvAHBQUZU6o4WJ719NrGBzSELBFVBI9XbxvOtYpmYpeV47bFVExkaxWaF_XR14PHtTZf7ILSEeamywJKwo_BYs9M&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&session_state=0c32992f0d47e93d273922018ade42d1072b9d1f..a35c&prompt=none#";
		
		String partualURL = url.split("code=")[1];
		String code = partualURL.split("&scope")[0];
		
		
	
		
		String accsessTokenResponse = 	given()
				.urlEncodingEnabled(false)
			.queryParams("code", code)
			.queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
			.queryParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
			.queryParams("redirect_url","https://rahulshettyacademy.com/getCourse.php")
			.queryParams("grant_type","authorization_code")
			.when().log().all()
			.post("https://www.googleapis.com/oauth2/v4/token").asString();
			
		JsonPath js = new JsonPath(accsessTokenResponse);
		String accessToken = js.getString("access_token");
			
		//Response as a string:
		
//		String response = given().queryParam("access_token",accessToken)
//		.when().log().all()
//		.get("https://rahulshettyacademy.com/getCourse.php").asString();
//	System.out.println(response);
	
	
		//Response as a java object:
		GetCourse gc = given().queryParam("access_token",accessToken).expect().defaultParser(Parser.JSON)
				.when()
				.get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);
		
		
		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getInstructor());
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
		
		List<Api> apiCourses = gc.getCourses().getApi();
		for (int i=0; i<apiCourses.size(); i++)
		{
			if (apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
				System.out.println(apiCourses.get(i).getPrice());
			}
		}
		
		
		// Get the courses name from WebAutomation
		String[] courseTitles = {"Selenium Webdriver Java","Cypress","Protractor"};  
	
		gc.getCourses().getWebAutomation().get(2).getCourseTitle();
		
		ArrayList<String> actualList = new ArrayList<String>();
		
		List<WebAutomation> waCourses = gc.getCourses().getWebAutomation();
		for (int i=0; i<waCourses.size(); i++ )
		{
			System.out.println(waCourses.get(i).getCourseTitle());
			actualList.add(waCourses.get(i).getCourseTitle());
		}
		
		List<String> expectedList = Arrays.asList(courseTitles);
		Assert.assertTrue(actualList.equals(expectedList));
	}

}
