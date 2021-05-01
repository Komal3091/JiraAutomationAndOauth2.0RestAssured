package files;

import io.restassured.path.json.JsonPath;

public class ReusableMethods {
	
	
	public static String rawToJsonToString(String getResponse, String parameter) {
		JsonPath jsonPath = new JsonPath(getResponse);
		return jsonPath.getString(parameter);
		
	}
}
