package basics;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.Assert;



public class JiraTest2 {

	public static void main(String[] args) {
		
		
		RestAssured.baseURI = "http://localhost:8080/";
		SessionFilter session = new SessionFilter();
		
		// User Authentication
		given()
			.relaxedHTTPSValidation()
			.header("Content-Type", "application/json")
			.body("{ \r\n"
					+ "    \"username\": \"komal3091\", \r\n"
					+ "\r\n"
					+ "    \"password\": \"******\" \r\n"
					+ "\r\n"
					+ "}")
			.log().all()
			.filter(session)
			.when()
			.post("rest/auth/1/session")
			.then()
			.assertThat()
			.statusCode(200)
			.extract()
			.response()
			.asString();
		
		
		// Create an issue
		/*String createResponse = given()
			.log().all()
			.header("Content-Type", "application/json")
			.body(JiraPayload.createBug())
			.filter(session)
			.when()
			.post("rest/api/2/issue")
			.then()
			.log().all()
			.assertThat()
			.statusCode(201)
			.extract()
			.response()
			.asString();*/
		
		// Add Comment
		String commentResponse = given()
				.pathParam("id", "10104")
				.log().all()
				.header("Content-Type", "application/json")
				.body("{\r\n"
						+ "    \"body\": \"This is validation comment for the fifth bug.\",\r\n"
						+ "    \"visibility\": {\r\n"
						+ "        \"type\": \"role\",\r\n"
						+ "        \"value\": \"Administrators\"\r\n"
						+ "    }\r\n"
						+ "}")
				.filter(session)
				.when()
				.post("rest/api/2/issue/{id}/comment")
				.then()
				.log().all()
				.assertThat()
				.statusCode(201)
				.extract()
				.response()
				.asString();
		JsonPath js = new JsonPath(commentResponse);
		int commentId = js.getInt("id");
				
		
		// Add attachment
		given()
		.log().all()
		.pathParam("id", "10104")
		.header("Content-Type", "multipart/form-data")
		.header("X-Atlassian-Token", "no-check")
		.filter(session)
		.multiPart("file", new File("D:\\Selenium Workspace\\RestAssuredDemo\\jira.txt"))
		.when()
		.post("rest/api/2/issue/{id}/attachments")
		.then()
		.log().all()
		.assertThat()
		.statusCode(200)
		.extract()
		.asString();
		
		//Get Issue
		String issueDetails = given()
				.filter(session)
				.pathParam("id", "10104")
				.queryParam("fields", "comment")
				.get("/rest/api/2/issue/{id}")
				.then()
				.log().all()
				.extract()
				.response()
				.asString();
		System.out.println(issueDetails);
		System.out.println(commentId);
		JsonPath jsonPath = new JsonPath(issueDetails);
		int commentsCount = jsonPath.getInt("fields.comment.comments.size()");
		System.out.println(commentsCount);
		for (int i=0; i<commentsCount; i++) {
			System.out.println(jsonPath.getInt("fields.comment.comments["+i+"].id"));
			if (jsonPath.getInt("fields.comment.comments["+i+"].id")==commentId) {
				System.out.println(jsonPath.getString("fields.comment.comments["+i+"].body"));
				Assert.assertEquals(jsonPath.getString("fields.comment.comments["+i+"].body"), "This is validation comment for the fifth bug.");
				break;
			}
		}
	}

}
