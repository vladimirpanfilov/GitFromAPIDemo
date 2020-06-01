import org.testng.Assert;
import org.testng.annotations.Test;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class SumVAlidation {
	
	@Test
	public void sumOfCourses () {
		
		
		JsonPath js = new JsonPath(Payload.coursePrice());

		int courseCount = js.getInt("courses.size()");
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		int countTotal = 0;
		
		for (int i = 0; i < courseCount; i++) {
			
			int coursePriceIterative = js.getInt("courses[" + i + "].price");
			int copiesIterative = js.getInt("courses[" + i + "].copies");
			int culculateTotal = coursePriceIterative * copiesIterative;
			countTotal = countTotal + culculateTotal;
			
		}
		System.out.println(countTotal); 
		Assert.assertEquals(totalAmount, countTotal);
	}

}
