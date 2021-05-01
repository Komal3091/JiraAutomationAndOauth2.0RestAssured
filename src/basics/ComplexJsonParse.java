package basics;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JsonPath jsonPath = new JsonPath(Payload.coursePrice());
		// Size of the array courses
		int courseArraySize = jsonPath.getInt("courses.size()");
		System.out.println(courseArraySize);
		//Print Purchase amount
		int totalAmount = jsonPath.getInt("dashboard.purchaseAmount");
		System.out.println(totalAmount);
		//Print title of the first course
		String firstCourseTitle = jsonPath.getString("courses[0].title");
		System.out.println(firstCourseTitle);
		//Print all courses titles and their respective prices
		for (int i=0; i<courseArraySize; i++) {
			System.out.println("Course"+" - "+jsonPath.getString("courses["+i+"].title")+" - "+"Price"+" - "+jsonPath.getInt("courses["+i+"].price"));
		}
		//Print number of copies sold by RPA course
		for (int i=0; i<courseArraySize; i++) {
			if (jsonPath.getString("courses["+i+"].title").equalsIgnoreCase("rpa")) {
				System.out.println("RPA no. of copies = "+jsonPath.getInt("courses["+i+"].copies"));
				break;
			}
		
		}
		
	
	
	}

}
