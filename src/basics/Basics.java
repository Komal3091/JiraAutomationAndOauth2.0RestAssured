package basics;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.Payload;
import files.ReusableMethods;

public class Basics {

	public static void main(String[] args) {
		
		//Validate if Add place API is working as expected
		//Given - All input Details
		//When - Sumbit the API - Resources and http method
		//Then - Validate the response
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json").body(
				Payload.addPlace()).when().post("maps/api/place/add/json").then()
		.assertThat().statusCode(200).body("scope", equalTo("APP")).header("server", "Apache/2.4.18 (Ubuntu)").extract().asString();
		System.out.println(response);
		JsonPath jsonPath = new JsonPath(response); //for parsing jason
		String placeId = jsonPath.getString("place_id");
		System.out.println(placeId);
		
		// Update place
		String newAddress = "Dwardevi Chowk, Bettiah";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}\r\n"
				+ "")
		.when().put("maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
	
		// Get place
		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
		.when().get("maps/api/place/get/json")
		.then().assertThat().statusCode(200).log().all()
		.extract().asString();
		
		System.out.println(ReusableMethods.rawToJsonToString(getPlaceResponse, "address"));
		Assert.assertEquals(ReusableMethods.rawToJsonToString(getPlaceResponse, "address"), newAddress);
	
	}

}
