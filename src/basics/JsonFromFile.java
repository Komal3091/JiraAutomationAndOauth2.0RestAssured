package basics;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;


import io.restassured.RestAssured;

public class JsonFromFile {
	
	// Content of the file to String -> Content of the file can be converted into bytes -> Byte data to String
	@Test
	public void jsonFromFileAddPlace() throws IOException {
		
		
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all()
				.queryParam("key", "qaclick123")
				.header("Content-Type", "application/json")
				.body(new String(Files.readAllBytes(Paths.get("D:\\Documents\\Google.json"))))
				.when()
				.post("maps/api/place/add/json")
				.then()
				.assertThat()
				.statusCode(200)
				.body("scope", equalTo("APP"))
				.header("server", "Apache/2.4.18 (Ubuntu)")
				.extract().asString();
		System.out.println(response);
	}
}
