import org.testng.Assert;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		JsonPath js = new JsonPath(Payload.coursePrice());

		// 1. Print No of courses returned by API

		int courseCount = js.getInt("courses.size()");
		System.out.println(courseCount);

		// 2.Print Purchase Amount
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println(totalAmount);

		// 3. Print Title of the first course

		String TitleFirstCouurse = js.getString("courses[0].title");
		System.out.println(TitleFirstCouurse);

		// 4. Print All course titles and their respective Prices

		for (int i = 0; i < courseCount; i++) {
			String titelIterative = js.get("courses[" + i + "].title");
			int coursePriceIterative = js.getInt("courses[" + i + "].price");
			System.out.println("Title: " + titelIterative + " Price: " + coursePriceIterative);

		}

		// 5. Print no of copies sold by RPA Course

		System.out.println("Print no of copies sold by RPA Course");

		for (int i = 0; i < courseCount; i++) {
			String titelIterative = js.get("courses[" + i + "].title");
			if (titelIterative.equalsIgnoreCase("RPA")) {
				int copiesIterative = js.getInt("courses[" + i + "].copies");
				System.out.println("Titel: " + titelIterative + " copies: " + copiesIterative);
				break;
			}
		}

		// 6. Verify if Sum of all Course prices matches with Purchase Amount
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
