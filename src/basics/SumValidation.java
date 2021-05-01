package basics;


import org.testng.Assert;
import org.testng.annotations.Test;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {

	@Test
	public void validateSumWithPurchaseAmount() {
		//Verify if sum of all course price matches with the purchase amount
		
		JsonPath jsonPath = new JsonPath(Payload.coursePrice());
		int sum = 0;
		for (int i=0; i< jsonPath.getInt("courses.size()"); i++) {
			sum = sum + (jsonPath.getInt("courses["+i+"].price")*jsonPath.getInt("courses["+i+"].copies"));
		}
		System.out.println(sum);
		Assert.assertEquals(jsonPath.getInt("dashboard.purchaseAmount"), sum);
	}


}