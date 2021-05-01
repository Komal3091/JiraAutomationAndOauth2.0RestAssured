package basics;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.Payload;
import files.ReusableMethods;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

public class DynamicJson {

	 
	@Test(dataProvider = "getData")
	public void addBook(String isbn, String aisle) {
		RestAssured.baseURI = "http://216.10.245.166";
		
		String response = given().log().all().header("Content-Type", "application/json").
				body(Payload.addBook(isbn, aisle)).when().post("Library/Addbook.php").then().assertThat().statusCode(200).
				extract().asString();
		//Extract id and then delete the book
		String id = ReusableMethods.rawToJsonToString(response, "ID");
		given().log().all().header("Content-Type", "application/json").
		body("{\r\n"
				+ " \r\n"
				+ "\"ID\" : \""+id+"\"\r\n"
				+ " \r\n"
				+ "} \r\n"
				+ "").when().post("Library/DeleteBook.php").then().log().all().assertThat().statusCode(200);
	}
	
	@DataProvider(name = "getData")
	public Object[][] getData(){
		
		
		Object[][] data = new Object[3][2];
		
		data[0][0] = "Krishna1";
		data[0][1] = "30911";

		data[1][0] = "Kkkkk1k";
		data[1][1] = "30911";
		
		data[2][0] = "Mayank1";
		data[2][1] = "27571";
		
		
		return data;
	}
}
