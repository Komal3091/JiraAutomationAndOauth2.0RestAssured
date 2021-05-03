package basics;
import static io.restassured.RestAssured.*;

import java.io.File;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;


public class JiraTest {
	
	public static void main(String[] args) {
		RestAssured.baseURI = "http://localhost:8080";
		SessionFilter session = new SessionFilter();
		
		given().header("Content-Type", "application/json").body("{ \r\n"
				+ "    \"username\": \"komal3091\", \r\n"
				+ "\r\n"
				+ "    \"password\": \"****\" \r\n"
				+ "\r\n"
				+ "}")
		.log().all().filter(session).when().post("/rest/auth/1/session").then().extract().response().asString();
		
		// Add comment to bug
		given().pathParam("id", "10023").log().all().header("Content-Type", "application/json").
		body("{\r\n"
				+ "    \"body\": \"This is my first comment.\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}").filter(session).when().
		post("/rest/api/2/issue/{id}/comment").then().log().all().assertThat().statusCode(201);
		
		// Add attachment
		given().pathParam("id", "10023").log().all().header("Content-Type", "multipart/form-data").header("X-Atlassian-Token", "no-check")
		.filter(session).multiPart("file", new File("jira.txt")).
		when().post("/rest/api/2/issue/{id}/attachments").then().log().all().assertThat().statusCode(200);
	
		//Get Issue
		String issueDetails = given().filter(session).pathParam("id", "10023").
				queryParam("fields", "comment").
				get("/rest/api/2/issue/{id}").then().log().all().extract().response().asString();
		System.out.println(issueDetails);
	}
	
	
	
	
	
}
