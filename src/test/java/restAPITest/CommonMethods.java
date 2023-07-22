package restAPITest;

import java.util.HashMap;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CommonMethods {
	public static String baseURI = "http://localhost:8088/employees";
	public static String accessToken;
	public static int empCount;
	RequestSpecification request;
	Response response;

	public Response getEmployee() {
		request = RestAssured.given().baseUri(baseURI);
		response = request.get();
		empCount = response.jsonPath().getList("employees").size();
		return response;
	}
	public Response getEmployee(int id) {
		request = RestAssured.given().baseUri(baseURI);
		response = request.get();
		empCount = response.jsonPath().getList("employees").size();
		return response;
	}

	public Response createEmployee(String fname, String lname, String sal, String email) {

		HashMap<String, Object> requestBody = new HashMap<String, Object>();
		requestBody.put("firstName", fname);
		requestBody.put("lasttName", lname);
		requestBody.put("salary", sal);
		requestBody.put("email", email);

		request = RestAssured.given();
		response = request.contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(requestBody).post();
		return response;
	}

	public Response updateEmployee(int id) {
		HashMap<String, Object> requestBody = new HashMap<String, Object>();
		requestBody.put("firstName", "Tom");

		request = RestAssured.given();

		response = request.contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(requestBody).put("/" + id);
		return response;
	}
	
	public Response deleteEmployee(int empId) {
		request = RestAssured.given().baseUri(baseURI);
		response = request.delete("/"+empId);
		return response;
	}

}
