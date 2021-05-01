package authDemo;


import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class OauthTest {

	public static void main(String[] args) {

		
		
		System.setProperty("webdriver.chrome.driver", "D:\\Selenium Workspace\\RestAssuredDemo\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
		driver.findElement(By.xpath("//*[@id=\"identifierId\"]")).sendKeys("krishnakomal2@gmail.com");
		driver.findElement(By.xpath("//*[@id=\"identifierNext\"]/div/button/div[2]")).click();
		driver.findElement(By.xpath("//*[@id=\"password\"]/div[1]/div/div[1]/input")).sendKeys("xxxxx");
		driver.findElement(By.xpath("//*[@id=\"passwordNext\"]/div/button/div[2]")).click();
		
		
		//Capturing the url
		String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AY0e-g6Nzm1po3wBROkIO5ybNG_Mu9hiIiQMVSw95kXPmlb7_bgChc7djo4VH2EuvV6vPg&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none#";
		String code = url.split("code=")[1].split("&scope")[0];
		
		
		
		//Generating access token response - POST
		//By default rest assured will convert the special characters to numerical value.
		//Since code has numerical values in it -  we have to explicitly mention it to not convert special characters
		String accessTokenResponse = given()
										.urlEncodingEnabled(false)
										.queryParam("code", code)
										.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
										.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
										.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
										.queryParam("grant_type", "authorization_code")
										.when()
										.log().all()
										.post("https://www.googleapis.com/oauth2/v4/token").asString();
		
		//Capturing the access token
		JsonPath jsonPath = new JsonPath(accessTokenResponse);
		String accessToken = jsonPath.getString("access_token");
		
		
		//Get Request
		String response = given()
							.queryParam("access_token", accessToken)
							.when()
							.log().all()
							.get("https://rahulshettyacademy.com/getCourse.php")
							.asString();
		
		System.out.println(response);
		

	}

}
