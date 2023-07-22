package restAPITest;


import java.util.List;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

	public class EndToEndTest extends CommonMethods {
		int empId;
		
		@Test
		public void runtestCases() {
			
			JsonPath json;
			List<String> names;
			Response response = getEmployee();
			// validating ResponseCode as 200
			Assert.assertEquals(response.statusCode(), 200);
			// Validating count of Employees
			Assert.assertEquals(empCount, 3);
			
			response = createEmployee("John", "Dae", "100000", "johnDae@gmail.com");
			// validating ResponseCode as 200
			Assert.assertEquals(response.statusCode(), 201);
			json = response.jsonPath();
			names = json.get("firstName");
			List<Integer> empIds = json.get("id");
			
			for(int i = 0;i<empIds.size();i++) {
				if(names.get(i).contains("John"))
				{
					Assert.assertEquals(names.get(i),Matchers.equalTo("John"));
					empId = empIds.get(i);
				}
			}
			// Validating the name in the response is John
			
			response = getEmployee();
			empCount = response.jsonPath().getList("employees").size();
			Assert.assertEquals(empCount, 5);

			response = updateEmployee(empId);
			Assert.assertEquals(response.statusCode(), 200);
			
			json = response.jsonPath();
			Assert.assertEquals(json.get("firstName"),"Tom");
			

			response = getEmployee();
			json = response.jsonPath();
			names = json.get("firstName");
			boolean flag = true;
			for(int i = 0;i<names.size();i++) {
				if(names.get(i).contains("John"))
				{
					flag = false;		
				}
			}
			Assert.assertEquals(flag, true);
			
			response = getEmployee(empId);
			json = response.jsonPath();
			Assert.assertEquals(json.get("firstName"),"Tom");
			Assert.assertEquals(response.statusCode(), 200);
		
			response = deleteEmployee(empId);
			Assert.assertEquals(response.statusCode(), 200);
			
			response=getEmployee(empId);
			Assert.assertEquals(response.statusCode(), 400);
			
			
			response = getEmployee();
			Assert.assertEquals(response.statusCode(), 200);
			Assert.assertEquals(empCount, 3);
		}
}
