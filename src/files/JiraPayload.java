package files;

import io.restassured.path.json.JsonPath;

public class JiraPayload {
		
	
	
	public static String createBug() {
		return " {\r\n"
				+ "     \r\n"
				+ "     \"fields\": {\r\n"
				+ "        \"project\": {\r\n"
				+ "            \"key\": \"KOM\"\r\n"
				+ "        },\r\n"
				+ "        \"summary\": \"Hello Fifth Bug\",\r\n"
				+ "        \"description\": \"Creating my Fifth bug\",\r\n"
				+ "        \"issuetype\": {\r\n"
				+ "            \"name\":  \"Bug\"\r\n"
				+ "        }\r\n"
				+ "     }\r\n"
				+ " }";
		
	}
	
	public static int getId(String response, String parameter) {
		
		JsonPath jsonPath = new JsonPath(response);
		
		return jsonPath.getInt(parameter);
	}
	
	
}
